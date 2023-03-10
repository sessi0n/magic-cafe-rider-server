package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserRegisterNpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRegisterNpcRepository extends JpaRepository<UserRegisterNpc, String> {
    List<UserRegisterNpc> findAllByUid(String uid);
    UserRegisterNpc findByIdx(Long nIdx);
}
