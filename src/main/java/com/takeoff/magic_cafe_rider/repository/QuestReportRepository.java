package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.QuestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestReportRepository extends JpaRepository<QuestReport, String> {
}
