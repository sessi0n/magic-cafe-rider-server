package com.takeoff.magic_cafe_rider.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeoff.magic_cafe_rider.enums.NpcTabType;
import com.takeoff.magic_cafe_rider.enums.NpcType;
import com.takeoff.magic_cafe_rider.manager.FileManager;
import com.takeoff.magic_cafe_rider.model.*;
import com.takeoff.magic_cafe_rider.protocol.*;
import com.takeoff.magic_cafe_rider.service.NpcService;
import com.takeoff.magic_cafe_rider.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/npc")
public class NpcController {
    private final NpcService npcService;
    private final UserService userService;
    private final FileManager fileManager;

    @PostMapping(value = "/list")
    public NpcListRes function(@RequestBody @Valid NpcListReq req, NpcListRes res) {
        List<Npc> npcs = npcService.getNpcList(req.getPageNum(), req.getPageSize());

        List<NpcListRes.NpcInfo> npcInfos = new ArrayList<>();
        npcs.forEach(n-> {
            NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
            npcInfo.setNpc(n);
            npcInfo.setNpcImages(npcService.getNpcImageList(n.getNid()));
            npcInfo.setUser(userService.getUserData(n.getUid()));

            npcInfos.add(npcInfo);
        });

        res.setNpcInfos(npcInfos);
        return res;
    }

    @PostMapping(value = "/list/area")
    public NpcListSearchAreaRes function(@RequestBody @Valid NpcListSearchAreaReq req, NpcListSearchAreaRes res) {
        List<Npc> npcs = npcService.getNpcList(req.getPageNum(), req.getPageSize(), req.getArea());

        List<NpcListRes.NpcInfo> npcInfos = new ArrayList<>();
        npcs.forEach(n-> {
            NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
            npcInfo.setNpc(n);
            npcInfo.setNpcImages(npcService.getNpcImageList(n.getNid()));
            npcInfo.setUser(userService.getUserData(n.getUid()));

            npcInfos.add(npcInfo);
        });

        res.setNpcInfos(npcInfos);
        return res;
    }

    @PostMapping(value = "/list/name")
    public NpcListSearchNameRes function(@RequestBody @Valid NpcListSearchNameReq req, NpcListSearchNameRes res) {
        List<Npc> npcs = npcService.getNpcList(req.getPageNum(), req.getPageSize(), req.getName());

        List<NpcListRes.NpcInfo> npcInfos = new ArrayList<>();
        npcs.forEach(n-> {
            NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
            npcInfo.setNpc(n);
            npcInfo.setNpcImages(npcService.getNpcImageList(n.getNid()));
            npcInfo.setUser(userService.getUserData(n.getUid()));

            npcInfos.add(npcInfo);
        });

        res.setNpcInfos(npcInfos);
        return res;
    }

    @PostMapping(value = "/list/job")
    public NpcListSearchJobRes function(@RequestBody @Valid NpcListSearchJobReq req, NpcListSearchJobRes res) {
        List<Npc> npcs = npcService.getNpcList(req.getPageNum(), req.getPageSize(), req.getNpcType());

        List<NpcListRes.NpcInfo> npcInfos = new ArrayList<>();
        npcs.forEach(n-> {
            NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
            npcInfo.setNpc(n);
            npcInfo.setNpcImages(npcService.getNpcImageList(n.getNid()));
            npcInfo.setUser(userService.getUserData(n.getUid()));

            npcInfos.add(npcInfo);
        });

        res.setNpcInfos(npcInfos);
        return res;
    }


    @PostMapping(value = "/add")
    public NpcAddRes function(@RequestPart("files") List<MultipartFile> files, @RequestParam("npc") String strNpc, NpcAddRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        Npc reqNpc = objectMapper.readValue(strNpc, new TypeReference<>() {});

        Npc npc = npcService.addNpc(reqNpc);
        npcService.addUserRegisterNpc(npc.getUid(), npc.getNid());
        List<UserRegisterNpc> userRegisterNpcs = npcService.getNpcList(npc.getUid());

        String thumbName = "";
        List<NpcImage> npcImages = new ArrayList<>();
        String dbPath = fileManager.makePath("n_"+npc.getNid().toString());

        for (MultipartFile f : files) {
            if (f.getSize() <= 0) {
                log.debug("push file error: {}", f.getOriginalFilename());
                continue;
            }

            log.info("push file : {}", f.getOriginalFilename());
            String fileName = fileManager.pushFile(f, dbPath, thumbName.isEmpty());

            if (thumbName.isEmpty()) {
                thumbName = fileManager.getThumbPath(dbPath, fileName);
                npc.setThumbnail(thumbName);
            }

            NpcImage npcImage = NpcImage.builder()
                    .nid(npc.getNid())
                    .imgUrl(dbPath)
                    .imgFile(fileName)
                    .build();
            npcImages.add(npcImage);
        }

        npcService.addNpcImages(npcImages);
        npcService.updateNpc(npc);

        res.setUserRegisterNpcs(userRegisterNpcs);
        return res;
    }

    @PostMapping(value = "/remove")
    public RemoveNpcRes function(@RequestBody @Valid RemoveNpcReq req, RemoveNpcRes res) {
        List<UserRegisterNpc> userRegisterNpcs = userService.getUserRegisterNpcs(req.getUid());

        UserRegisterNpc userRegisterNpc = userRegisterNpcs.stream().filter(x -> Objects.equals(x.getIdx(), req.getNpcIdx()))
                .findFirst().orElse(null);

        if (userRegisterNpc != null) {
            Npc npc = npcService.getNpc(userRegisterNpc.getNid());
            npc.setDeleted(true);
            npcService.updateNpc(npc);
            npcService.delUserRegisterNpc(userRegisterNpc);
            userRegisterNpcs.remove(userRegisterNpc);
            log.info("Success RemoveNpcReq");
            log.info("RemoveNpcReq Success {}", req);
        }

        res.setMyNpcs(userRegisterNpcs);

        return res;
    }

    @PostMapping(value = "/modify/youtube_url")
    public NpcModifyYoutubeUrlRes function(@RequestBody @Valid NpcModifyYoutubeUrlReq req, NpcModifyYoutubeUrlRes res) {
        Npc npc = npcService.getNpc(req.getId());

        if (!Objects.equals(npc.getUid(), req.getUid())) {
            return res;
        }

        npc.setYoutubeUrl(req.getUrl());

        npcService.updateNpc(npc);

        NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
        npcInfo.setNpc(npc);
        npcInfo.setNpcImages(npcService.getNpcImageList(npc.getNid()));
        npcInfo.setUser(userService.getUserData(npc.getUid()));
        res.setNpcInfo(npcInfo);

        return res;
    }

    @PostMapping(value = "/modify/web_url")
    public NpcModifyWebUrlRes function(@RequestBody @Valid NpcModifyWebUrlReq req, NpcModifyWebUrlRes res) {
        Npc npc = npcService.getNpc(req.getId());

        if (!Objects.equals(npc.getUid(), req.getUid())) {
            return res;
        }

        npc.setWebUrl(req.getUrl());

        npcService.updateNpc(npc);

        NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
        npcInfo.setNpc(npc);
        npcInfo.setNpcImages(npcService.getNpcImageList(npc.getNid()));
        npcInfo.setUser(userService.getUserData(npc.getUid()));
        res.setNpcInfo(npcInfo);

        return res;
    }

    @PostMapping(value = "/modify/with_picture")
    public NpcModifyRes function(@RequestPart("files") List<MultipartFile> files
            , @RequestParam("npc") String strNpc
            , @RequestParam("delImages") String delImages
            , NpcModifyRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        Npc reqNpc = objectMapper.readValue(strNpc, new TypeReference<>() {});
        List<Long> reqDelImages = objectMapper.readValue(delImages, new TypeReference<List<Long>>() {});

        Npc modifyNpc = npcService.getNpc(reqNpc.getNid());
        if (!Objects.equals(modifyNpc.getUid(), reqNpc.getUid())) {
            return res;
        }

        if (!reqDelImages.isEmpty()) {
            List<NpcImage> images = npcService.getNpcImageList(reqNpc.getNid());
            images.forEach(i -> {
                boolean found = reqDelImages.stream().anyMatch(f-> f.equals(i.getIdx()));
                if (found) {
                    i.setDeleted(true);
                    npcService.updateNpcImage(i);
                }
            });
        }

        String dbPath = fileManager.makePath("n_"+modifyNpc.getNid().toString());
        if (!files.isEmpty()) {
            List<NpcImage> npcImages = new ArrayList<>();

            for (MultipartFile f : files) {
                if (f.getSize() <= 0) {
                    log.debug("push file error: {}", f.getOriginalFilename());
                    continue;
                }

                log.info("push file : {}", f.getOriginalFilename());
                String fileName = fileManager.pushFile(f, dbPath, false);

                NpcImage npcImage = NpcImage.builder()
                        .nid(modifyNpc.getNid())
                        .imgUrl(dbPath)
                        .imgFile(fileName)
                        .build();
                npcImages.add(npcImage);
            }

            npcService.addNpcImages(npcImages);
        }

        //썸내일 다시 만들어야 하는데 구조 변경 필요.
        modifyNpc.setName(reqNpc.getName());
        modifyNpc.setLocation(reqNpc.getLocation());
        modifyNpc.setArea(reqNpc.getArea());
        modifyNpc.setCity(reqNpc.getCity());
        modifyNpc.setLat(reqNpc.getLat());
        modifyNpc.setLng(reqNpc.getLng());
        modifyNpc.setType(reqNpc.getType());

        npcService.updateNpc(modifyNpc);

        NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
        npcInfo.setNpc(modifyNpc);
        npcInfo.setNpcImages(npcService.getNpcImageList(modifyNpc.getNid()));
        npcInfo.setUser(userService.getUserData(modifyNpc.getUid()));

        res.setNpcInfo(npcInfo);

        return res;
    }

    @PostMapping(value = "/modify/no_picture")
    public NpcModifyRes function(@RequestBody @Valid NpcModifyReq req, NpcModifyRes res) {
        Npc reqNpc = req.getNpc();
        Npc modifyNpc = npcService.getNpc(reqNpc.getNid());

        if (!Objects.equals(modifyNpc.getUid(), reqNpc.getUid())) {
            return res;
        }

        if (!req.getDelImages().isEmpty()) {
            List<NpcImage> images = npcService.getNpcImageList(reqNpc.getNid());
            images.forEach(i -> {
                boolean found = req.getDelImages().stream().anyMatch(f-> f.equals(i.getIdx()));
                if (found) {
                    i.setDeleted(true);
                    npcService.updateNpcImage(i);
                }
            });
        }

        modifyNpc.setName(reqNpc.getName());
        modifyNpc.setLocation(reqNpc.getLocation());
        modifyNpc.setArea(reqNpc.getArea());
        modifyNpc.setCity(reqNpc.getCity());
        modifyNpc.setLat(reqNpc.getLat());
        modifyNpc.setLng(reqNpc.getLng());
        modifyNpc.setType(reqNpc.getType());

        npcService.updateNpc(modifyNpc);

        NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
        npcInfo.setNpc(modifyNpc);
        npcInfo.setNpcImages(npcService.getNpcImageList(modifyNpc.getNid()));
        npcInfo.setUser(userService.getUserData(modifyNpc.getUid()));
        res.setNpcInfo(npcInfo);

        return res;
    }

    @GetMapping(value = "/id/{id}")
    public GetNpc function(@PathVariable long id, GetNpc res) {
        Npc npc = npcService.getNpc(id);
        User user = userService.getUserData(npc.getUid());

        res.setNpcImages(npcService.getNpcImageList(npc.getNid()));
        res.setNpc(npc);
        res.setUser(user);
        return res;
    }
    @PostMapping(value = "/list/tab")
    public NpcListTabTypeRes function(@RequestBody @Valid NpcListTabTypeReq req, NpcListTabTypeRes res) {
        List<NpcType> types = new ArrayList<>();

        if (req.getTabType() == NpcTabType.WITH_BIKE) {
            types.add(NpcType.BRAND);
            types.add(NpcType.ACADEMY);
            types.add(NpcType.ASSIST);
            types.add(NpcType.ENGINE);
            types.add(NpcType.STORE);
            types.add(NpcType.WEBSTORE);
        }
        else {
            types.add(NpcType.FOOD);
            types.add(NpcType.CAMPING);
        }

        List<Npc> npcs = npcService.getNpcList(req.getPageNum(), req.getPageSize(), types);

        List<NpcListRes.NpcInfo> npcInfos = new ArrayList<>();
        npcs.forEach(n-> {
            NpcListRes.NpcInfo npcInfo = new NpcListRes.NpcInfo();
            npcInfo.setNpc(n);
            npcInfo.setNpcImages(npcService.getNpcImageList(n.getNid()));
            npcInfo.setUser(userService.getUserData(n.getUid()));

            npcInfos.add(npcInfo);
        });

        res.setNpcInfos(npcInfos);
        return res;
    }

    @GetMapping(value = "/images/{nid}")
    public GetNpcImages function(@PathVariable long nid, GetNpcImages res) {
        List<NpcImage> npcImages = npcService.getNpcImageList(nid);

        res.setNpcImages(npcImages);
        return res;
    }
}
