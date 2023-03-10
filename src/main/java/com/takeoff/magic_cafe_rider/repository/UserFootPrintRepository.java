package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserFootPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserFootPrintRepository extends JpaRepository<UserFootPrint, String>  {
    List<UserFootPrint> findAllByUidOrderByIdxDesc(String uid);
//    @Query("SELECT a, MAX(a.date) from UserFootPrint as a where a.uid = :uid group by a.qid")
//    @Query("select a from UserFootPrint  as a where a.uid = :uid")
//    List<UserFootPrint> findDistinctCustom(String uid);
    void deleteAllByUid(String uid);
    Optional<UserFootPrint> findTopByUidAndQidOrderByDateDesc(String uid, Long qid);
}
