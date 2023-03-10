package com.takeoff.magic_cafe_rider.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeoff.magic_cafe_rider.enums.FootType;
import com.takeoff.magic_cafe_rider.enums.FriendState;
import com.takeoff.magic_cafe_rider.enums.MarkerType;
import com.takeoff.magic_cafe_rider.enums.ServiceType;
import com.takeoff.magic_cafe_rider.manager.FileManager;
import com.takeoff.magic_cafe_rider.model.*;
import com.takeoff.magic_cafe_rider.protocol.*;
import com.takeoff.magic_cafe_rider.service.NpcService;
import com.takeoff.magic_cafe_rider.service.QuestService;
import com.takeoff.magic_cafe_rider.service.SystemConfService;
import com.takeoff.magic_cafe_rider.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final QuestService questService;
    private final NpcService npcService;
    private final SystemConfService systemService;
    private final FileManager fileManager;


    @PostMapping(value = "/version")
    public VersionRes function(@RequestBody @Valid VersionReq req, VersionRes res) {
        String service = req.getService();
        String version = "";
        if (Objects.equals(service, "android")) {
            version = systemService.getVersion("android");
        } else if (Objects.equals(service, "ios")) {
            version = systemService.getVersion("ios");
        } else {
            version = systemService.getVersion("version");
        }

        res.setVersion(version);
        return res;
    }

    @PostMapping(value = "/my_profile")
    public MyProfileRes function(@RequestBody @Valid MyProfileReq req, MyProfileRes res) {
        User user = userService.getUserData(req.getUid());
        ServiceType serviceType = req.getService() == null ? ServiceType.NONE : req.getService();

        if (user == null) {
            user = userService.newUserCreate(req.getUid(), req.getEmail(), req.getNick(), req.getAvatar(), serviceType);
        } else {
            if (!Objects.equals(user.getAvatar(), req.getAvatar())
                    || !Objects.equals(user.getEmail(), req.getEmail())
                    || !Objects.equals(user.getNick(), req.getNick())
                    || !Objects.equals(user.getService(), serviceType)) {
                user.setAvatar(req.getAvatar());
                user.setEmail(req.getEmail());
                user.setNick(req.getNick());
                user.setService(serviceType);
                userService.update(user);
            }

            List<User> bikers = userService.getFavoriteBikers(user.getUid());
            res.setFavoriteBikers(bikers);
            res.setMyQuests(userService.getUserQuestList(user.getUid()));

            List<UserRegisterQuest> registerQuests = userService.getUserRegisterQuests(user.getUid());
            List<UserRegisterNpc> registerNpcs = userService.getUserRegisterNpcs(user.getUid());

            res.setRegisterQuests(registerQuests);
            res.setRegisterNpcs(registerNpcs);
        }

        res.setUser(user);
        return res;
    }

    @PostMapping(value = "/favorite")
    public FavoriteRes function(@RequestBody @Valid FavoriteReq req, FavoriteRes res) {
        User user = userService.getUserData(req.getUid());
        User biker = userService.getUserData(req.getTarget());

        if (user == null || biker == null) {
            return res;
        }

        List<UserFavoriteBiker> userFavoriteBikers = userService.getUserFavoriteBiker(user.getUid());
        UserFavoriteBiker temp = userFavoriteBikers.stream().filter(e -> Objects.equals(e.getBiker(), biker.getUid())).findFirst().orElse(null);

        if (temp != null) {
            userService.delUserFavoriteBiker(temp);

            userFavoriteBikers.remove(temp);
            res.setRetCode(2);
        } else {
            UserFavoriteBiker userFavoriteBiker = UserFavoriteBiker.builder()
                    .uid(user.getUid())
                    .biker(biker.getUid())
                    .build();
            userService.saveUserFavoriteBikerRepository(userFavoriteBiker);

            userFavoriteBikers.add(userFavoriteBiker);
            res.setRetCode(1);
        }

        List<String> uids = new ArrayList<>();
        for (UserFavoriteBiker f : userFavoriteBikers) {
            uids.add(f.getBiker());
        }

        List<User> users = userService.getUserList(uids);
        res.setFavoriteBikers(users);

        return res;
    }

    @PostMapping(value = "/biker")
    public BikerInfoRes function(@RequestBody @Valid BikerInfoReq req, BikerInfoRes res) {
        User user = userService.getUserData(req.getUid());
        List<UserRegisterQuest> registerQuests = userService.getUserRegisterQuests(user.getUid());

        List<Long> dataRegister = registerQuests.stream().map(UserRegisterQuest::getQid).collect(Collectors.toList());
        List<Quest> quests = questService.getQuestList(dataRegister);

        List<BikerInfoRes.BikerQuest> bikerQuests = new ArrayList<>();
        quests.forEach(q -> {
            BikerInfoRes.BikerQuest bikerQuest = new BikerInfoRes.BikerQuest();
            bikerQuest.setQuest(q);
            bikerQuest.setQuestImages(questService.getQuestImageList(q.getQid()));

            bikerQuests.add(bikerQuest);
        });
        res.setUser(user);
        res.setBikerQuests(bikerQuests);

        return res;
    }

    @PostMapping(value = "/ranker")
    public RankerInfoRes function(@RequestBody @Valid RankerInfoReq req, RankerInfoRes res) {
        User user = userService.getUserData(req.getUid());
//        List<UserQuest> completedQuests = userService.getUserQuestList(user.getUid());
        List<Quest> quests = questService.getQuestRegister(req.getUid(), true);

//        List<Long> dataRegister = completedQuests.stream().map(UserQuest::getQid).collect(Collectors.toList());
//        List<Quest> quests = questService.getQuestList(dataRegister);

        List<RankerInfoRes.BikerQuest> bikerQuests = new ArrayList<>();
        quests.forEach(q -> {
            RankerInfoRes.BikerQuest bikerQuest = new RankerInfoRes.BikerQuest();
            bikerQuest.setQuest(q);
            bikerQuest.setQuestImages(questService.getQuestImageList(q.getQid()));

            bikerQuests.add(bikerQuest);
        });
        res.setUser(user);
        res.setBikerQuests(bikerQuests);

        return res;
    }

    @PostMapping(value = "/my_bike")
    public MyMotoRes function(@RequestBody @Valid MyMotoReq req, MyMotoRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            user.setBikeBrand(req.getBikeBrand());
            user.setBikeModel(req.getBikeModel());

            userService.update(user);
            res.setUid(user.getUid());
        }

        return res;
    }

    @PostMapping(value = "/update/road_sign")
    public UserRoadSignRes function(@RequestPart("files") List<MultipartFile> files, @RequestParam("road_sign") String strRoadSign, UserRoadSignRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        UserRoadSignReq reqRoadSign = objectMapper.readValue(strRoadSign, new TypeReference<>() {
        });

        User user = userService.getUserData(reqRoadSign.getUid());
        String dbPath = fileManager.makeRoadSignPath(reqRoadSign.getUid());

        for (MultipartFile f : files) {
            if (f.getSize() <= 0) {
                log.debug("push file error: {}", f.getOriginalFilename());
                continue;
            }

            log.info("push file : {}", f.getOriginalFilename());
            String fileName = fileManager.pushCommonFile(f, dbPath);

            user.setRoadSignUrl(dbPath);
            user.setRoadSignFile(fileName);
        }

        userService.update(user);

        res.setUser(user);

        return res;
    }

    @PostMapping(value = "/youtube")
    public MyYoutubeRes function(@RequestBody @Valid MyYoutubeReq req, MyYoutubeRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            user.setYoutube(req.getUrl());

            userService.update(user);
            res.setUid(user.getUid());
        }

        return res;
    }

    @PostMapping(value = "/instagram")
    public MyInstagramRes function(@RequestBody @Valid MyInstagramReq req, MyInstagramRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            user.setInstagram(req.getUrl());

            userService.update(user);
            res.setUid(user.getUid());
        }

        return res;
    }

    @PostMapping(value = "/service_codec")
    public SetServiceCodecRes function(@RequestBody @Valid SetServiceCodecReq req, SetServiceCodecRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            user.setServiceCodec(true);

            userService.update(user);
            res.setUid(user.getUid());
        }

        return res;
    }

    @PostMapping(value = "/block")
    public UserBlockRes function(@RequestBody @Valid UserBlockReq req, UserBlockRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            List<UserBlock> userBlockList = userService.getUserBlockList(user.getUid());
            boolean found = userBlockList.stream().anyMatch(f -> Objects.equals(f.getTargetUid(), req.getTargetUid()));
            if (!found) {
                UserBlock userBlock = UserBlock.builder()
                        .idx(0L)
                        .uid(req.getUid())
                        .targetUid(req.getTargetUid())
                        .build();

                userService.addUserBlock(userBlock);
            }
            res.setUid(user.getUid());
        }

        return res;
    }

    @PostMapping(value = "/block_list")
    public UserBlockListRes function(@RequestBody @Valid UserBlockListReq req, UserBlockListRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            List<UserBlock> userBlockList = userService.getUserBlockList(user.getUid());

            List<String> uids = new ArrayList<>();
            for (UserBlock f : userBlockList) {
                uids.add(f.getTargetUid());
            }

            List<User> users = userService.getUserList(uids);
            res.setUserBlockList(users);
//            res.setUserBlockList(userBlockList);
        }

        return res;
    }

    @PostMapping(value = "/nonblock")
    public UserNonBlockRes function(@RequestBody @Valid UserNonBlockReq req, UserNonBlockRes res) {
        User user = userService.getUserData(req.getUid());
        if (user != null) {
            List<UserBlock> userBlockList = userService.getUserBlockList(user.getUid());
            UserBlock userBlock = userBlockList.stream().filter(x -> Objects.equals(x.getTargetUid(), req.getTargetUid())).findFirst().orElse(null);

            if (userBlock != null) {
                userService.delUserBlock(userBlock);
                userBlockList.remove(userBlock);
            }

            List<String> uids = new ArrayList<>();
            for (UserBlock f : userBlockList) {
                uids.add(f.getTargetUid());
            }

            List<User> users = userService.getUserList(uids);
            res.setUserBlockList(users);
        }

        return res;
    }

    @PostMapping(value = "/marker")
    public MarkerRes function(@RequestBody @Valid MarkerReq req, MarkerRes res) {
        List<Quest> quests = questService.getQuestMarkerInfo();
        List<Npc> npcs = npcService.getNpcMarkerInfo();

        List<MarkerRes.MarkerInfo> markers = new ArrayList<>();
        npcs.forEach(n -> {
            MarkerRes.MarkerInfo markerInfo = new MarkerRes.MarkerInfo();
            markerInfo.setType(MarkerType.NPC);
            markerInfo.setId(n.getNid());
            markerInfo.setTitle(n.getName());
            markerInfo.setLat(n.getLat());
            markerInfo.setLng(n.getLng());
            markers.add(markerInfo);
        });
        quests.forEach(q -> {
            MarkerRes.MarkerInfo markerInfo = new MarkerRes.MarkerInfo();
            markerInfo.setType(MarkerType.QUEST);
            markerInfo.setId(q.getQid());
            markerInfo.setTitle(q.getName());
            markerInfo.setLat(q.getLat());
            markerInfo.setLng(q.getLng());
            markers.add(markerInfo);
        });

        res.setMarkerInfos(markers);

        return res;
    }

    @PostMapping(value = "/agit")
    public MyAgitRes function(@RequestBody @Valid MyAgitReq req, MyAgitRes res) {
        User user = userService.getUserData(req.getUid());
        Quest quest = questService.getQuest(req.getQid());

        user.setAgit(quest.getQid());
        userService.update(user);

        res.setUid(user.getUid());

        return res;
    }

    @PostMapping(value = "/leave")
    public LeaveRes function(@RequestBody @Valid LeaveReq req, LeaveRes res) {
        User user = userService.getUserData(req.getUid());
        String uid = user.getUid();

        if (user.getRole() == 1) { //admin은 탈퇴안됨
            return res;
        }

        userService.remove(user);

        res.setUid(uid);
        return res;
    }

    @GetMapping(value = "/foot/{uid}")
    public GetFootPrint function(@PathVariable String uid, GetFootPrint res) {
        User user = userService.getUserData(uid);
        List<UserQuest> userQuests = userService.getUserCompletedQuestList(user.getUid());
        List<UserFootPrint> userFootPrints = userService.getUserFootList(user.getUid());

        List<GetFootPrint.FootPrintLog> footPrintLogs = new ArrayList<>();
        if (!userQuests.isEmpty()) {
            List<Quest> quests = new ArrayList<>();
            quests.addAll(questService.getQuestList(userQuests.stream().map(UserQuest::getQid).toList()));
            userQuests.forEach((e) -> {
                Quest quest = quests.stream().filter(x -> Objects.equals(e.getQid(), x.getQid())).findFirst().orElse(null);
                if (quest != null) {
                    GetFootPrint.FootPrintLog userFootPrint = new GetFootPrint.FootPrintLog();
                    userFootPrint.setQid(quest.getQid());
                    userFootPrint.setName(quest.getName());
                    userFootPrint.setType(FootType.COMPLETED);
                    userFootPrint.setDate(e.getCompletedDate());
                    userFootPrint.setCan(false);
                    footPrintLogs.add(userFootPrint);
                }
            });
        }
        if (!userFootPrints.isEmpty()) {
//            LocalDateTime now = LocalDateTime.now();
//            LocalDate lnow = now.minusHours(10).toLocalDate();

            List<Quest> quests = new ArrayList<>();
            quests.addAll(questService.getQuestList(userFootPrints.stream().map(UserFootPrint::getQid).toList()));
            userFootPrints.forEach((e) -> {
                Quest quest = quests.stream().filter(x -> Objects.equals(e.getQid(), x.getQid())).findFirst().orElse(null);
                if (quest != null) {
//                    LocalDateTime foot = e.getDate();
//                    LocalDate lfoot = foot.minusHours(10).toLocalDate();

                    GetFootPrint.FootPrintLog userFootPrint = new GetFootPrint.FootPrintLog();
                    userFootPrint.setQid(quest.getQid());
                    userFootPrint.setName(quest.getName());
                    userFootPrint.setType(e.getType());
                    userFootPrint.setDate(e.getDate());
//                    userFootPrint.setCan(ChronoUnit.DAYS.between(lfoot, lnow) > 0);
                    userFootPrint.setCan(false);

                    footPrintLogs.add(userFootPrint);
                }
            });
        }

        res.setFootPrintLogs(footPrintLogs);

        return res;
    }

    @GetMapping(value = "/foot_last/{uid}")
    public GetLastFootPrint function(@PathVariable String uid, GetLastFootPrint res) {
        User user = userService.getUserData(uid);
        List<UserQuest> userQuests = userService.getUserCompletedQuestList(user.getUid());
        List<UserFootPrint> userFootPrints = userService.getUserFootList(user.getUid());
        List<UserFootPrint> distinctFootPrints = new ArrayList<>();

        userQuests.forEach(e-> {
            UserFootPrint f = UserFootPrint.builder()
                    .qid(e.getQid())
                    .type(FootType.COMPLETED)
                    .uid(user.getUid())
                    .date(e.getCompletedDate())
                    .build();
            userFootPrints.add(f);
        });

        userFootPrints.forEach((e) -> {
            UserFootPrint find = distinctFootPrints.stream().filter(s-> Objects.equals(s.getQid(), e.getQid())).findFirst().orElse(null);
            if (find == null) {
                distinctFootPrints.add(e);
            }
            else {
                if (find.getDate().isBefore(e.getDate())){
                    int index = distinctFootPrints.indexOf(find);
                    distinctFootPrints.set(index, e);
                }
            }
        });

        List<GetFootPrint.FootPrintLog> footPrintLogs = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        LocalDate lnow = now.minusHours(10).toLocalDate();

        if (!distinctFootPrints.isEmpty()) {
            List<Quest> quests = new ArrayList<>(questService.getQuestList(distinctFootPrints.stream().map(UserFootPrint::getQid).toList()));
            distinctFootPrints.forEach((e) -> {
                Quest quest = quests.stream().filter(x -> Objects.equals(e.getQid(), x.getQid())).findFirst().orElse(null);
                if (quest != null) {
                    LocalDateTime foot = e.getDate();
                    LocalDate lfoot = foot.minusHours(10).toLocalDate();

                    GetFootPrint.FootPrintLog userFootPrint = new GetFootPrint.FootPrintLog();
                    userFootPrint.setQid(quest.getQid());
                    userFootPrint.setName(quest.getName());
                    userFootPrint.setType(e.getType());
                    userFootPrint.setDate(e.getDate());
                    userFootPrint.setCan(ChronoUnit.DAYS.between(lfoot, lnow) > 0);

                    footPrintLogs.add(userFootPrint);
                }
            });
        }

        res.setFootPrintLogs(footPrintLogs);

        return res;
    }

    @PostMapping(value = "/owner")
    public OwnerRes function(@RequestBody @Valid OwnerReq req, OwnerRes res) {
        User user = userService.getUserData(req.getUid());
        if (user.getRole() != 1) {
            res.setSuccess(false);
            return res;
        }

        User target = userService.getUserData(req.getTid());
        Quest cafe = questService.getQuest(req.getQid());

        if (target == null || cafe == null) {
            res.setSuccess(false);
            return res;
        }

        Owner owner = userService.setOwner(target, cafe.getQid());

        res.setSuccess(true);

        return res;
    }

    @PostMapping(value = "/owner/word")
    public OwnerWordRes function(@RequestBody @Valid OwnerWordReq req, OwnerWordRes res) {
        User user = userService.getUserData(req.getUid());
        if (user.getRole() == 0) {
            return res;
        }

        Owner owner = userService.getOwner(req.getUid());

        owner.setWord(req.getWord());

        userService.update(owner);

        res.setOwner(owner);

        return res;
    }

    @GetMapping(value = "/owner/{uid}")
    public GetOwner function(@PathVariable String uid, GetOwner res) {
        User user = userService.getUserData(uid);
        if (user.getRole() == 0) {
            return res;
        }

        Owner owner = userService.getOwner(uid);
        res.setOwner(owner);

        return res;
    }

    @GetMapping(value = "/friends/{uid}")
    public GetFriends function(@PathVariable String uid, GetFriends res) {
        log.info("GetFriends: {}", uid);

        User user = userService.getUserData(uid);

        List<UserFriend> userFriends = userService.getUserFriends(user.getUid());

        List<GetFriends.Friend> friendList = new ArrayList<>();
        userFriends.forEach(f -> {
            GetFriends.Friend friend = new GetFriends.Friend();
            friend.setUser(userService.getUserData(f.getFid()));
            friend.setState(f.getState());

            friendList.add(friend);
        });

        res.setFriendList(friendList);

        return res;
    }

    @PutMapping(value = "/friends")
    public AddFriendRes function(@RequestBody @Valid AddFriendReq req, AddFriendRes res) throws Exception {
        User user = userService.getUserData(req.getUid());
        List<User> friends = new ArrayList<>();
        if (!req.getEmail().isEmpty()) {
            log.info("email: {}", req.getEmail());
            if (req.getEmail().equals(user.getEmail())) {
                throw new Exception("Can not add friend, it's me by email");
            }
            friends.addAll(userService.getUserDataByEmail(req.getEmail()));
            log.info("friends: {}", friends.size());
        }
        else if (!req.getFid().isEmpty()) {
            log.info("fid: {}", req.getFid());
            if (req.getFid().equals(user.getUid())) {
                throw new Exception("Can not add friend, it's me by fid");
            }
            friends.add(userService.getUserData(req.getFid()));
        }

        if (friends.isEmpty()) {
            log.debug("add friends error: {}", req);
            throw new Exception("Can not add friend");
        }

        List<UserFriend> userFriends = new ArrayList<>();
        List<UserFriend> targetFriends = new ArrayList<>();
        friends.forEach(f -> {
            log.info("friends: {}", f.getUid());
            UserFriend userFriend = UserFriend.builder()
                    .uid(user.getUid())
                    .fid(f.getUid())
                    .state(FriendState.REQUEST)
                    .build();
            UserFriend targetFriend = UserFriend.builder()
                    .uid(f.getUid())
                    .fid(user.getUid())
                    .state(FriendState.RESPONSE)
                    .build();
            userFriends.add(userFriend);
            targetFriends.add(targetFriend);
        });

        userService.saveUserFriends(userFriends);
        userService.saveUserFriends(targetFriends);

        List<UserFriend> myFriends = userService.getUserFriends(user.getUid());
        List<AddFriendRes.Friend> resFriends = new ArrayList<>();
        myFriends.forEach(f -> {
            AddFriendRes.Friend friend = new AddFriendRes.Friend();
            friend.setUser(userService.getUserData(f.getFid()));
            friend.setState(f.getState());

            resFriends.add(friend);
        });

        res.setFriendList(resFriends);
        return res;
    }

    @PostMapping(value = "/friends")
    public UpdateFriendRes function(@RequestBody @Valid UpdateFriendReq req, UpdateFriendRes res) throws Exception {
        User user = userService.getUserData(req.getUid());

        UserFriend userFriend = userService.getUserFriend(user.getUid(), req.getFid());
        UserFriend targetFriend = userService.getUserFriend(req.getFid(), user.getUid());
        if (req.isUnpair()) {
            userFriend.setState(FriendState.UNPAIR);
            userService.saveUserFriend(userFriend);
        }
        else if (req.isPairing()) {
            userFriend.setState(FriendState.PAIRING);
            userService.saveUserFriend(userFriend);
        }
        else if (req.isAccept()) {
            userFriend.setState(FriendState.PAIRING);
            targetFriend.setState(FriendState.PAIRING);
            userService.saveUserFriend(userFriend);
            userService.saveUserFriend(targetFriend);
        }
        else {
            throw new Exception("invalid req data");
        }

        List<UserFriend> myFriends = userService.getUserFriends(user.getUid());
        List<UpdateFriendRes.Friend> resFriends = new ArrayList<>();
        myFriends.forEach(f -> {
            UpdateFriendRes.Friend friend = new UpdateFriendRes.Friend();
            friend.setUser(userService.getUserData(f.getFid()));
            friend.setState(f.getState());

            resFriends.add(friend);
        });

        res.setFriendList(resFriends);
        return res;
    }

    @DeleteMapping(value = "/friends/{uid}/{tid}")
    public GetFriends function(@PathVariable String uid, @PathVariable String tid, GetFriends res) {
        log.info("GetFriends: {}", uid);

        User user = userService.getUserData(uid);
        UserFriend userFriend = userService.getUserFriend(user.getUid(), tid);
        UserFriend targetFriend = userService.getUserFriend(tid, user.getUid());

        userService.removeUserFriend(userFriend);
        userService.removeUserFriend(targetFriend);


        List<UserFriend> userFriends = userService.getUserFriends(user.getUid());

        List<GetFriends.Friend> friendList = new ArrayList<>();
        userFriends.forEach(f -> {
            GetFriends.Friend friend = new GetFriends.Friend();
            friend.setUser(userService.getUserData(f.getFid()));
            friend.setState(f.getState());

            friendList.add(friend);
        });

        res.setFriendList(friendList);

        return res;
    }
}
