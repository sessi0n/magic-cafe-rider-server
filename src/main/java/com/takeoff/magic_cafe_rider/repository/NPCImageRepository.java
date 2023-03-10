package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.NpcImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPCImageRepository extends JpaRepository<NpcImage, String> {
    List<NpcImage> findAllByNidAndDeletedIsFalse(Long nid);
}
