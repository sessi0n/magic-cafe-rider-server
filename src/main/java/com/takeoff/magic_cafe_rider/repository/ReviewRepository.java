package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, String>  {
    List<Review> findAllByQidAndDeletedIsFalse(Long qid);
    Review findByRid(Long rid);
}
