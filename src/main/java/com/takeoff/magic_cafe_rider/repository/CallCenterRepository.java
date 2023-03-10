package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.CallCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallCenterRepository extends JpaRepository<CallCenter, String> {
}
