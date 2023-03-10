package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, String>  {
    List<UserQuest> findAllByUidOrderByIdxDesc(String uid);
    List<UserQuest> findAllByUidAndCompletedIsTrueOrderByIdxDesc(String uid);
    void deleteAllByUid(String uid);
    UserQuest findByUidAndQid(String uid, Long qid);
}
