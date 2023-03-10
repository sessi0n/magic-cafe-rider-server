package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.enums.NpcType;
import com.takeoff.magic_cafe_rider.model.Npc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPCRepository extends JpaRepository<Npc, String> {
    List<Npc> findAllByDeletedIsFalseOrderByCreatedTimeDesc(Pageable pageable);
    List<Npc> findAllByAreaAndDeletedIsFalseOrderByCreatedTimeDesc(int area, Pageable pageable);
    List<Npc> findAllByNameContainingAndDeletedIsFalseOrderByCreatedTimeDesc(String name, Pageable pageable);
    List<Npc> findAllByTypeAndDeletedIsFalseOrderByCreatedTimeDesc(NpcType type, Pageable pageable);

    Npc findByNid(Long nid);

    List<Npc> findAllByTypeNotAndDeletedIsFalse(NpcType type);
    List<Npc> findAllByTypeInAndDeletedIsFalseOrderByTypeAsc(List<NpcType> types, Pageable pageable);
}
