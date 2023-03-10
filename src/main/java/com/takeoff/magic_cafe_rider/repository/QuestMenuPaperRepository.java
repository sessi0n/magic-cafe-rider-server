package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.QuestMenuPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestMenuPaperRepository extends JpaRepository<QuestMenuPaper, Long>  {
    QuestMenuPaper findByQid(long Qid);
}
