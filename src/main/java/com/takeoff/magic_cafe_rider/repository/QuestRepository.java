package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.enums.QuestType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestRepository extends JpaRepository<com.takeoff.magic_cafe_rider.model.Quest, String>  {
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByAreaAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(int area, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByNameContainingAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(String name, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByTypeAndDeletedIsFalseAndUidNotInOrderByCompleteCountDescCreatedTimeDesc(com.takeoff.magic_cafe_rider.enums.QuestType type, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByQidInAndDeletedIsFalseOrderByCompleteCountDescCreatedTimeDesc(List<Long> qids);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByQidInAndDeletedIsFalse(List<Long> qids);

    //SortType.NEW
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByAreaAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(int area, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByNameContainingAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(String name, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByTypeAndDeletedIsFalseAndUidNotInOrderByCreatedTimeDesc(com.takeoff.magic_cafe_rider.enums.QuestType type, List<String> blocks, Pageable pageable);
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByQidInAndDeletedIsFalseOrderByCreatedTimeDesc(List<Long> qids);


    com.takeoff.magic_cafe_rider.model.Quest findByQid(Long qid);

    @Query("SELECT a FROM Quest AS a inner JOIN UserQuest AS b ON a.qid = b.qid where b.uid = :uid AND b.completed = :isCompleted AND a.deleted = false ORDER BY b.idx DESC")
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllWithUserQuestRegister(String uid, boolean isCompleted);

    @Query("SELECT a FROM Quest AS a inner JOIN UserRegisterQuest AS b ON a.qid = b.qid where b.uid = :uid AND a.deleted = false ORDER BY b.idx DESC")
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllUserRegister(String uid);

    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByDeletedIsFalse();
    List<com.takeoff.magic_cafe_rider.model.Quest> findAllByTypeAndDeletedIsFalseOrderByNameAsc(QuestType type);
}
