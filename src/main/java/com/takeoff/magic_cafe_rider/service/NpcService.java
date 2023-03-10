package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.enums.NpcType;
import com.takeoff.magic_cafe_rider.model.Npc;
import com.takeoff.magic_cafe_rider.model.NpcImage;
import com.takeoff.magic_cafe_rider.model.UserRegisterNpc;
import com.takeoff.magic_cafe_rider.repository.NPCImageRepository;
import com.takeoff.magic_cafe_rider.repository.NPCRepository;
import com.takeoff.magic_cafe_rider.repository.UserRegisterNpcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NpcService {
    private final NPCRepository npcRepository;
    private final NPCImageRepository npcImageRepository;
    private final UserRegisterNpcRepository userRegisterNpcRepository;

    public List<Npc> getNpcList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return npcRepository.findAllByDeletedIsFalseOrderByCreatedTimeDesc(pageable);
    }
    public List<Npc> getNpcList(int pageNum, int pageSize, int area) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return npcRepository.findAllByAreaAndDeletedIsFalseOrderByCreatedTimeDesc(area, pageable);
    }
    public List<Npc> getNpcList(int pageNum, int pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return npcRepository.findAllByNameContainingAndDeletedIsFalseOrderByCreatedTimeDesc(name, pageable);
    }
    public List<Npc> getNpcList(int pageNum, int pageSize, NpcType type) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return npcRepository.findAllByTypeAndDeletedIsFalseOrderByCreatedTimeDesc(type, pageable);
    }

    public List<NpcImage> getNpcImageList(Long nid) {
        return npcImageRepository.findAllByNidAndDeletedIsFalse(nid);
    }

    public Npc addNpc(Npc npc) {
        return npcRepository.saveAndFlush(npc);
    }

    public List<NpcImage> addNpcImages(List<NpcImage> npcImages) {
        return npcImageRepository.saveAll(npcImages);
    }

    public Npc updateNpc(Npc npc) {
        return npcRepository.save(npc);
    }

    public void addUserRegisterNpc(String uid, Long nid) {
        UserRegisterNpc userRegisterNpc = UserRegisterNpc.builder()
                .uid(uid)
                .nid(nid)
                .build();

        userRegisterNpcRepository.save(userRegisterNpc);
    }

    public List<UserRegisterNpc> getNpcList(String uid) {
        return userRegisterNpcRepository.findAllByUid(uid);
    }

    public Npc getNpc(Long nid) {
        return npcRepository.findByNid(nid);
    }

    public void delUserRegisterNpc(UserRegisterNpc userRegisterNpc) {
        userRegisterNpcRepository.delete(userRegisterNpc);
    }

    public List<Npc> getNpcMarkerInfo() {

        return npcRepository.findAllByTypeNotAndDeletedIsFalse(NpcType.WEBSTORE);
    }

    public List<Npc> getNpcList(int pageNum, int pageSize, List<NpcType> types) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return npcRepository.findAllByTypeInAndDeletedIsFalseOrderByTypeAsc(types, pageable);
    }

    public NpcImage updateNpcImage(NpcImage npcImage) {
        return npcImageRepository.save(npcImage);
    }
}
