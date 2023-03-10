package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.QuestImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestImageRepository extends JpaRepository<QuestImage, String>  {
    List<QuestImage> findAllByQidAndDeletedIsFalse(Long qid);
//    void deleteByQidAndIdxIn(Long qid, List<Long> ids);

}
