package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserRegisterQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRegisterQuestRepository extends JpaRepository<UserRegisterQuest, String>  {
    List<UserRegisterQuest> findAllByUidOrderByIdxDesc(String uid);
    UserRegisterQuest findByIdx(Long qIdx);
}
