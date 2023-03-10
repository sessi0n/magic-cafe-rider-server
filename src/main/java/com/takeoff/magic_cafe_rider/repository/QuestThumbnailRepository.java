package com.takeoff.magic_cafe_rider.repository;


import com.takeoff.magic_cafe_rider.model.QuestThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestThumbnailRepository extends JpaRepository<QuestThumbnail, String>  {

}
