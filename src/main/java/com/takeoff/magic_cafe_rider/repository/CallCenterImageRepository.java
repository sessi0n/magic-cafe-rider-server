package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.CallCenterImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallCenterImageRepository extends JpaRepository<CallCenterImage, String> {
}
