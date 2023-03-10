package com.takeoff.magic_cafe_rider.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeoff.magic_cafe_rider.enums.FootType;
import com.takeoff.magic_cafe_rider.enums.QuestDataReqType;
import com.takeoff.magic_cafe_rider.enums.SortType;
import com.takeoff.magic_cafe_rider.manager.FileManager;
import com.takeoff.magic_cafe_rider.model.*;
import com.takeoff.magic_cafe_rider.protocol.*;
import com.takeoff.magic_cafe_rider.service.QuestService;
import com.takeoff.magic_cafe_rider.service.RankService;
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
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/quest")
public class QuestController {
    private final QuestService questService;

    private final UserService userService;
    private final FileManager fileManager;
    private final RankService rankService;

    @PostMapping(value = "/list")
    public QuestListRes function(@RequestBody @Valid QuestListReq req, QuestListRes res) {
        List<String> blockList = new ArrayList<>();

        blockList.add("");

        SortType sort = SortType.COMPLETE_COUNT;
        if (req.getSortType() != null) {
            sort = req.getSortType();
        }

        List<Quest> quests = questService.getQuestList(req.getPageNum(), req.getPageSize(), blockList, sort);

        List<QuestListRes.QuestInfo> questInfos = new ArrayList<>();
        quests.forEach(q-> {
            QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
            questInfo.setQuest(q);
            questInfo.setQuestImages(questService.getQuestImageList(q.getQid()));
            questInfo.setUser(userService.getUserData(q.getUid()));

            questInfos.add(questInfo);
        });

        res.setQuestInfos(questInfos);
        return res;
    }

    @PostMapping(value = "/list/name")
    public QuestListSearchNameRes function(@RequestBody @Valid QuestListSearchNameReq req, QuestListSearchNameRes res) {
        List<String> blockList = new ArrayList<>();

//        if (req.getUid() != null) {
//            List<UserBlock> userBlockList = userService.getUserBlockList(req.getUid());
//            blockList.addAll(userBlockList.stream().map(UserBlock::getTargetUid).toList());
//        }

        blockList.add("");

        SortType sort = SortType.COMPLETE_COUNT;
        if (req.getSortType() != null) {
            sort = req.getSortType();
        }

        List<Quest> quests = questService.getQuestList(req.getPageNum(), req.getPageSize(), req.getName(), blockList, sort);

        List<QuestListRes.QuestInfo> questInfos = new ArrayList<>();
        quests.forEach(q-> {
            QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
            questInfo.setQuest(q);
            questInfo.setQuestImages(questService.getQuestImageList(q.getQid()));
            questInfo.setUser(userService.getUserData(q.getUid()));

            questInfos.add(questInfo);
        });

        res.setQuestInfos(questInfos);
        return res;
    }
    @PostMapping(value = "/list/job")
    public QuestListSearchJobRes function(@RequestBody @Valid QuestListSearchJobReq req, QuestListSearchJobRes res) {
        List<String> blockList = new ArrayList<>();

//        if (req.getUid() != null) {
//            List<UserBlock> userBlockList = userService.getUserBlockList(req.getUid());
//            blockList.addAll(userBlockList.stream().map(UserBlock::getTargetUid).toList());
//        }

        blockList.add("");

        SortType sort = SortType.COMPLETE_COUNT;
        if (req.getSortType() != null) {
            sort = req.getSortType();
        }


        List<Quest> quests = questService.getQuestList(req.getPageNum(), req.getPageSize(), req.getQuestType(), blockList, sort);

        List<QuestListRes.QuestInfo> questInfos = new ArrayList<>();
        quests.forEach(q-> {
            QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
            questInfo.setQuest(q);
            questInfo.setQuestImages(questService.getQuestImageList(q.getQid()));
            questInfo.setUser(userService.getUserData(q.getUid()));

            questInfos.add(questInfo);
        });

        res.setQuestInfos(questInfos);

        return res;
    }
    @PostMapping(value = "/list/area")
    public QuestListSearchAreaRes function(@RequestBody @Valid QuestListSearchAreaReq req, QuestListSearchAreaRes res) {
        List<String> blockList = new ArrayList<>();

//        if (req.getUid() != null) {
//            List<UserBlock> userBlockList = userService.getUserBlockList(req.getUid());
//            blockList.addAll(userBlockList.stream().map(UserBlock::getTargetUid).toList());
//        }

        blockList.add("");

        SortType sort = SortType.COMPLETE_COUNT;
        if (req.getSortType() != null) {
            sort = req.getSortType();
        }


        List<Quest> quests = questService.getQuestList(req.getPageNum(), req.getPageSize(), req.getArea(), blockList, sort);

        List<QuestListRes.QuestInfo> questInfos = new ArrayList<>();
        quests.forEach(q-> {
            QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
            questInfo.setQuest(q);
            questInfo.setQuestImages(questService.getQuestImageList(q.getQid()));
            questInfo.setUser(userService.getUserData(q.getUid()));

            questInfos.add(questInfo);
        });

        res.setQuestInfos(questInfos);
        return res;
    }

    @PostMapping(value = "/add")
    public QuestAddRes function(@RequestPart("files") List<MultipartFile> files, @RequestParam("quest") String strQuest, QuestAddRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        Quest reqQuest = objectMapper.readValue(strQuest, new TypeReference<>() {});
        reqQuest.setAcceptCount(0);
        reqQuest.setCompleteCount(0);
        reqQuest.setDeleted(false);
        reqQuest.setLevel(0);
        reqQuest.setThumbId(0L);

        Quest quest = questService.addQuest(reqQuest);
        userService.addUserRegisterQuest(quest.getUid(), quest.getQid());

        List<UserRegisterQuest> userRegisterQuest = userService.getUserRegisterQuests(quest.getUid());

        String thumbName = "";
        List<QuestImage> questImages = new ArrayList<>();
        String dbPath = fileManager.makePath("q_"+quest.getQid().toString());

        for (MultipartFile f : files) {
            if (f.getSize() <= 0) {
                log.debug("push file error: {}", f.getOriginalFilename());
                continue;
            }

            log.info("push file : {}", f.getOriginalFilename());
            String fileName = fileManager.pushFile(f, dbPath, thumbName.isEmpty());

            if (thumbName.isEmpty()) {
                thumbName = fileManager.getThumbPath(dbPath, fileName);
                quest.setThumbnail(thumbName); //과거

                //NEW
                QuestThumbnail thumb = QuestThumbnail.builder()
                        .thumbId(0L)
                        .url(fileManager.getThumbPathAttr(dbPath))
                        .file(fileManager.getThumbFileAttr(fileName))
                        .build();
                thumb = questService.saveThumb(thumb);
                quest.setThumbId(thumb.getThumbId());
            }

            QuestImage questImage = QuestImage.builder()
                    .qid(quest.getQid())
                    .imgUrl(dbPath)
                    .imgFile(fileName)
                    .build();
            questImages.add(questImage);
        }

        questService.addQuestImages(questImages);
        questService.updateQuest(quest);

        res.setUserRegisterQuests(userRegisterQuest);

        return res;
    }

    @PostMapping(value = "/my_quest_info")
    public MyQuestDataRes function(@RequestBody @Valid MyQuestDataReq req, MyQuestDataRes res) {
        List<Quest> quests = new ArrayList<>();
        if (req.getType() == QuestDataReqType.REGISTER) {
            quests.addAll(userService.getUserRegisterQuests2(req.getUid()));
        }
        else if (req.getType() == QuestDataReqType.ACCEPTED) {
            quests.addAll(questService.getQuestRegister(req.getUid(), false));
        }
        else if (req.getType() == QuestDataReqType.COMPLETED){
            quests.addAll(questService.getQuestRegister(req.getUid(), true));
        }

        List<QuestListRes.QuestInfo> questInfos = new ArrayList<>();
        quests.forEach(q-> {
            QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
            questInfo.setQuest(q);
            questInfo.setQuestImages(questService.getQuestImageList(q.getQid()));
            questInfo.setUser(userService.getUserData(q.getUid()));

            questInfos.add(questInfo);
        });

        res.setQuestInfos(questInfos);

        return res;
    }

    @PostMapping(value = "/aoc") //accept or cancel
    public UserQuestAoCRes function(@RequestBody @Valid UserQuestAoCReq req, UserQuestAoCRes res) {
        List<UserQuest> userQuests = userService.getUserQuestList(req.getUid());
        UserQuest userQuest = userQuests.stream().filter(x->Objects.equals(x.getQid(), req.getQid())).findFirst().orElse(null);

        Quest quest = questService.getQuest(req.getQid());

        if (userQuest != null) {
            //취소
            userService.delUserQuest(userQuest);
            userQuests.remove(userQuest);
            questService.saveAcceptCount(quest, -1);
            res.setAcceptQuests(userQuests);
            res.setRetCode(1);
            return res;
        }

        //승락
        userQuests.add( userService.addUserQuest(req.getUid(), req.getQid()));
        res.setAcceptQuests(userQuests);

        questService.saveAcceptCount(quest, 1);
        res.setRetCode(0);

        res.setQuest(quest);

        return res;
    }

    @PostMapping(value = "/complete")
    public CompleteQuestRes function(@RequestBody @Valid CompleteQuestReq req, CompleteQuestRes res) {
        User user = userService.getUserData(req.getUid());

        if (user == null) {
            res.setSuccess(false);
            return res;
        }

        List<UserQuest> userQuests = userService.getUserQuestList(req.getUid());
        UserQuest userQuest = userQuests.stream().filter(x -> Objects.equals(x.getQid(), req.getQid()))
                .filter(UserQuest::isCompleted)
                .findFirst().orElse(null);

        if (userQuest != null) {
            res.setSuccess(false);
            return res;
        }

        int userScore = userService.completeQuest(req.getQid(), req.getUid());
        rankService.updateOnePlayerRank(req.getUid(), userScore);

        res.setMyQuests(userService.getUserQuestList(user.getUid()));
        res.setScore(userScore);
        res.setSuccess(true);
        return res;
    }

    @PostMapping(value = "/remove")
    public RemoveQuestRes function(@RequestBody @Valid RemoveQuestReq req, RemoveQuestRes res) {
        List<UserRegisterQuest> userRegisterQuests = userService.getUserRegisterQuests(req.getUid());

        UserRegisterQuest userRegisterQuest = userRegisterQuests.stream().filter(x -> Objects.equals(x.getIdx(), req.getQuestIdx())).findFirst().orElse(null);

        if (userRegisterQuest != null) {
            Quest quest = questService.getQuest(userRegisterQuest.getQid());
            quest.setDeleted(true);
            questService.updateQuest(quest);
            userService.delUserRegisterQuest(userRegisterQuest);
            userRegisterQuests.remove(userRegisterQuest);
            log.info("Success RemoveQuestReq");
        }

        res.setMyQuests(userRegisterQuests);

        return res;
    }

    @PostMapping(value = "/report")
    public ReportQuestRes function(@RequestBody @Valid ReportQuestReq req, ReportQuestRes res) {
        log.info("ReportQuestRes {}", req);

        Quest quest = questService.getQuest(req.getQuestIdx());
        User user = userService.getUserData(req.getUid());

        if (quest != null && user != null) {
            questService.setReportQuest(user.getUid(), quest.getQid());
        }

        res.setUid(req.getUid());
        return res;
    }

    @PostMapping(value = "/modify/with_picture")
    public QuestModifyRes function(@RequestPart("files") List<MultipartFile> files
            , @RequestParam("quest") String strQuest
            , @RequestParam("delImages") String delImages
            , QuestModifyRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        Quest reqQuest = objectMapper.readValue(strQuest, new TypeReference<>() {});
        List<Long> reqDelImages = objectMapper.readValue(delImages, new TypeReference<List<Long>>() {});

        Quest modifyQuest = questService.getQuest(reqQuest.getQid());
        if (!Objects.equals(modifyQuest.getUid(), reqQuest.getUid())) {
            return res;
        }

        if (!reqDelImages.isEmpty()) {
            List<QuestImage> images = questService.getQuestImageList(reqQuest.getQid());
            images.forEach(i -> {
                boolean found = reqDelImages.stream().anyMatch(f-> f.equals(i.getIdx()));
                if (found) {
                    i.setDeleted(true);
                    questService.updateQuestImage(i);
                }
            });
        }

        String dbPath = fileManager.makePath("q_"+modifyQuest.getQid().toString());
        if (!files.isEmpty()) {
            List<QuestImage> questImages = new ArrayList<>();

            for (MultipartFile f : files) {
                if (f.getSize() <= 0) {
                    log.debug("push file error: {}", f.getOriginalFilename());
                    continue;
                }

                log.info("push file : {}", f.getOriginalFilename());
                String fileName = fileManager.pushFile(f, dbPath, false);

                QuestImage questImage = QuestImage.builder()
                        .qid(modifyQuest.getQid())
                        .imgUrl(dbPath)
                        .imgFile(fileName)
                        .build();
                questImages.add(questImage);
            }

            questService.addQuestImages(questImages);
        }

        //썸내일 다시 만들어야 하는데 구조 변경 필요.
        modifyQuest.setName(reqQuest.getName());
        modifyQuest.setLocation(reqQuest.getLocation());
        modifyQuest.setArea(reqQuest.getArea());
        modifyQuest.setCity(reqQuest.getCity());
        modifyQuest.setLat(reqQuest.getLat());
        modifyQuest.setLng(reqQuest.getLng());
        modifyQuest.setType(reqQuest.getType());
//        modifyQuest.setYoutubeUrl(reqQuest.getYoutubeUrl());

        questService.updateQuest(modifyQuest);

        QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
        questInfo.setQuest(modifyQuest);
        questInfo.setQuestImages(questService.getQuestImageList(modifyQuest.getQid()));
        questInfo.setUser(userService.getUserData(modifyQuest.getUid()));

        res.setQuestInfo(questInfo);

        return res;
    }

    @PostMapping(value = "/modify/no_picture")
    public QuestModifyRes function(@RequestBody @Valid QuestModifyReq req, QuestModifyRes res) {
        Quest reqQuest = req.getQuest();
        Quest modifyQuest = questService.getQuest(reqQuest.getQid());

        if (!Objects.equals(modifyQuest.getUid(), reqQuest.getUid())) {
            return res;
        }

        if (!req.getDelImages().isEmpty()) {
            List<QuestImage> images = questService.getQuestImageList(reqQuest.getQid());
            images.forEach(i -> {
                boolean found = req.getDelImages().stream().anyMatch(f-> f.equals(i.getIdx()));
                if (found) {
                    i.setDeleted(true);
                    questService.updateQuestImage(i);
                }
            });
        }

        modifyQuest.setName(reqQuest.getName());
        modifyQuest.setLocation(reqQuest.getLocation());
        modifyQuest.setArea(reqQuest.getArea());
        modifyQuest.setCity(reqQuest.getCity());
        modifyQuest.setLat(reqQuest.getLat());
        modifyQuest.setLng(reqQuest.getLng());
        modifyQuest.setType(reqQuest.getType());
//        modifyQuest.setYoutubeUrl(reqQuest.getYoutubeUrl());

        questService.updateQuest(modifyQuest);

        QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
        questInfo.setQuest(modifyQuest);
        questInfo.setQuestImages(questService.getQuestImageList(modifyQuest.getQid()));
        questInfo.setUser(userService.getUserData(modifyQuest.getUid()));
        res.setQuestInfo(questInfo);

        return res;
    }

    @PostMapping(value = "/modify/youtube_url")
    public QuestModifyYoutubeUrlRes function(@RequestBody @Valid QuestModifyYoutubeUrlReq req, QuestModifyYoutubeUrlRes res) {
        Quest modifyQuest = questService.getQuest(req.getQid());

        if (!Objects.equals(modifyQuest.getUid(), req.getUid())) {
            return res;
        }

        modifyQuest.setYoutubeUrl(req.getYoutubeUrl());

        questService.updateQuest(modifyQuest);

        QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
        questInfo.setQuest(modifyQuest);
        questInfo.setQuestImages(questService.getQuestImageList(modifyQuest.getQid()));
        questInfo.setUser(userService.getUserData(modifyQuest.getUid()));
        res.setQuestInfo(questInfo);

        return res;
    }

    @PostMapping(value = "/modify/instagram")
    public QuestModifyInstagramRes function(@RequestBody @Valid QuestModifyInstagramReq req, QuestModifyInstagramRes res) {
        Quest modifyQuest = questService.getQuest(req.getQid());

        if (!Objects.equals(modifyQuest.getUid(), req.getUid())) {
            return res;
        }

        modifyQuest.setInstagram(req.getInstagram());

        questService.updateQuest(modifyQuest);

        QuestListRes.QuestInfo questInfo = new QuestListRes.QuestInfo();
        questInfo.setQuest(modifyQuest);
        questInfo.setQuestImages(questService.getQuestImageList(modifyQuest.getQid()));
        questInfo.setUser(userService.getUserData(modifyQuest.getUid()));
        res.setQuestInfo(questInfo);

        return res;
    }

    @GetMapping(value = "/id/{id}")
    public GetQuest function(@PathVariable long id, GetQuest res) {
        Quest quest = questService.getQuest(id);
        User user = userService.getUserData(quest.getUid());

        res.setQuestImages(questService.getQuestImageList(quest.getQid()));
        res.setQuest(quest);
        res.setUser(user);
        return res;
    }

    @GetMapping(value = "/review/list/{uid}/{qid}")
    public GetReviewList function(@PathVariable long qid, @PathVariable String uid, GetReviewList res) {
        List<Review> reviews = questService.getReviews(qid);
        List<String> uids = new ArrayList<>(reviews.stream().map(Review::getUid).toList());
        List<UserBlock> userBlockList = userService.getUserBlockList(uid);
        uids.removeIf(x -> userBlockList.stream().anyMatch(f-> Objects.equals(x, f.getTargetUid())));

        List<User> users = userService.getUserList(uids);

        List<GetReviewList.ReviewInfo> reviewInfos = new ArrayList<>();

        reviews.forEach(e-> {
            User user = users.stream().findFirst().orElse(null);
            if (user != null) {
                GetReviewList.ReviewInfo reviewInfo = new GetReviewList.ReviewInfo();
                reviewInfo.setReview(e);
                reviewInfo.setUser(user);
                reviewInfos.add(reviewInfo);
            }
        });

        res.setReviewInfos(reviewInfos);

        return res;
    }

    @GetMapping(value = "/review/{id}")
    public GetReview function(@PathVariable long id, GetReview res) {
        List<Review> reviews = questService.getReviews(id);

        res.setReviews(reviews);
        return res;
    }

    @PostMapping(value = "/review/add")
    public ReviewAddRes function(@RequestBody @Valid ReviewAddReq req, ReviewAddRes res) {
        User user = userService.getUserData(req.getUid());
        Quest quest = questService.getQuest(req.getQid());

        Review review = Review.builder()
                .rid(0L)
                .uid(user.getUid())
                .qid(quest.getQid())
                .context(req.getContext())
                .deleted(false)
                .build();

        questService.addReview(review);

        List<Review> reviews = questService.getReviews(quest.getQid());
        List<String> uids = new ArrayList<>(reviews.stream().map(Review::getUid).toList());
        List<UserBlock> userBlockList = userService.getUserBlockList(req.getUid());
        uids.removeIf(x -> userBlockList.stream().anyMatch(f-> Objects.equals(x, f.getTargetUid())));

        List<User> users = userService.getUserList(uids);

        List<GetReviewList.ReviewInfo> reviewInfos = new ArrayList<>();

        reviews.forEach(e-> {
            User reviewer = users.stream().findFirst().orElse(null);
            if (reviewer != null) {
                GetReviewList.ReviewInfo reviewInfo = new GetReviewList.ReviewInfo();
                reviewInfo.setReview(e);
                reviewInfo.setUser(reviewer);
                reviewInfos.add(reviewInfo);
            }
        });

        res.setReviewInfos(reviewInfos);
        return res;
    }

    @DeleteMapping(value = "/review/{rid}/{uid}/{qid}")
    public GetReviewList function(@PathVariable long rid, @PathVariable String uid, @PathVariable long qid, GetReviewList res) {
        Review review = questService.getReview(rid);

        if (!Objects.equals(review.getUid(), uid) || !Objects.equals(review.getQid(), qid)) {
            return res;
        }

        questService.delReview(review);

        List<Review> reviews = questService.getReviews(qid);
        List<String> uids = new ArrayList<>(reviews.stream().map(Review::getUid).toList());
        List<UserBlock> userBlockList = userService.getUserBlockList(uid);
        uids.removeIf(x -> userBlockList.stream().anyMatch(f-> Objects.equals(x, f.getTargetUid())));

        List<User> users = userService.getUserList(uids);

        List<GetReviewList.ReviewInfo> reviewInfos = new ArrayList<>();

        reviews.forEach(e-> {
            User user = users.stream().findFirst().orElse(null);
            if (user != null) {
                GetReviewList.ReviewInfo reviewInfo = new GetReviewList.ReviewInfo();
                reviewInfo.setReview(e);
                reviewInfo.setUser(user);
                reviewInfos.add(reviewInfo);
            }
        });

        res.setReviewInfos(reviewInfos);
        return res;
    }

    @GetMapping(value = "/agit/{uid}")
    public GetAgit function(@PathVariable String uid, GetAgit res) {
        List<Quest> quests = questService.getQuestAgits();

        List<GetAgit.AgitInfo> agitInfos = new ArrayList<>();
        quests.forEach((e) -> {
            GetAgit.AgitInfo agit = new GetAgit.AgitInfo();
            agit.setQid(e.getQid());
            agit.setName(e.getName());
            agitInfos.add(agit);
        });

        res.setAgitInfos(agitInfos);

        return res;
    }

    @PostMapping(value = "/foot")
    public FootQuestRes function(@RequestBody @Valid FootQuestReq req, FootQuestRes res) {
        User user = userService.getUserData(req.getUid());

        if (user == null) {
            res.setSuccess(false);
            return res;
        }

        Quest quest = questService.getQuest(req.getQid());
        UserQuest userQuest = userService.getUserQuest(user.getUid(), quest.getQid());

        if(!userQuest.isCompleted()) {
            res.setSuccess(false);
            return res;
        }
        UserFootPrint userFootPrint = userService.getUserFootLast(user.getUid(), quest.getQid());
        if (userFootPrint != null) {
            LocalDateTime foot = userFootPrint.getDate();
            LocalDateTime now = LocalDateTime.now();
            LocalDate lfoot = foot.minusHours(10).toLocalDate();
            LocalDate lnow = now.minusHours(10).toLocalDate();

            long diff = ChronoUnit.DAYS.between(lfoot, lnow);
            log.info("FootPrint Time check foot:{} now:{} lf:{} ln:{} diff:{}", foot, now, lfoot, lnow, diff);
            if (diff <= 0) {
                res.setSuccess(false);
                return res;
            }
        }

        userService.addUserFootPrint(user.getUid(), quest.getQid(), FootType.NORMAL);
        user.setScore(user.getScore() + 1);
        rankService.updateOnePlayerRank(req.getUid(), user.getScore());

        userService.update(user);

        res.setSuccess(true);
        res.setScore(user.getScore());

        return res;
    }

    @GetMapping(value = "/owner/{qid}")
    public GetOwner function(@PathVariable Long qid, GetOwner res) {
        Owner owner = userService.getOwner(qid);
        res.setOwner(owner);

        return res;
    }

    @PostMapping(value = "/update/thumb")
    public QuestGateChangeRes function(@RequestBody @Valid QuestGateChangeReq req, QuestGateChangeRes res) throws Exception {
        Quest quest = questService.getQuest(req.getQid());
        List<QuestImage> questImages = questService.getQuestImageList(quest.getQid());

        QuestImage srcImage = questImages.stream().filter(e -> Objects.equals(e.getIdx(), req.getSrcPid())).findFirst().orElse(null);
        QuestImage decImage = questImages.stream().filter(e -> Objects.equals(e.getIdx(), req.getDecPid())).findFirst().orElse(null);

        if (srcImage == null || decImage == null) {
            return res;
        }

        String thumbFile = fileManager.updateThumbnail(decImage);

        QuestImage temp = QuestImage.builder()
                .qid(srcImage.getQid())
                .imgUrl(srcImage.getImgUrl())
                .imgFile(srcImage.getImgFile())
                .build();

        srcImage.setImgFile(decImage.getImgFile());
        srcImage.setImgUrl(decImage.getImgUrl());

        decImage.setImgFile(temp.getImgFile());
        decImage.setImgUrl(temp.getImgUrl());

        questService.updateQuestImage(srcImage);
        questService.updateQuestImage(decImage);

        quest.setThumbnail(thumbFile);
        questService.updateQuest(quest);

        return res;
    }

    @GetMapping(value = "/images/{qid}")
    public GetQuestImages function(@PathVariable Long qid, GetQuestImages res) {
        List<QuestImage> questImages = questService.getQuestImageList(qid);
        res.setQuestImages(questImages);

        return res;
    }

    @GetMapping(value = "/menu_paper/{qid}")
    public GetMenuPaper function(@PathVariable long qid, GetMenuPaper res) {

        QuestMenuPaper menuPaper = questService.getQuestMenuPaper(qid);

        res.setMenuPaper(menuPaper);
        return res;
    }

    @PostMapping(value = "/menu_paper/upload")
    public GetMenuPaper function(@RequestPart("file") MultipartFile file
            , @RequestParam("quest") long qid
            , GetMenuPaper res) throws Exception {
        Quest quest = questService.getQuest(qid);

        String dbPath = fileManager.makeMenuPaperPath(qid);
        if (file.getSize() <= 0) {
            log.debug("push file error: {}", file.getOriginalFilename());
            throw new Exception("File not found");
        }

        log.info("push file : {}", file.getOriginalFilename());
        String fileName = fileManager.pushFile(file, dbPath, false);

        QuestMenuPaper questMenuPaper = QuestMenuPaper.builder()
                .qid(qid)
                .imgFile(fileName)
                .imgUrl(dbPath)
                .deleted(false)
                .build();

        questService.addQuestMenuPaper(questMenuPaper);

        res.setMenuPaper(questMenuPaper);

        return res;
    }
}
