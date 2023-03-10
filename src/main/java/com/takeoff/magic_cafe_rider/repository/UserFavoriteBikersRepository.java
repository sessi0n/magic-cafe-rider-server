package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserFavoriteBiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserFavoriteBikersRepository extends JpaRepository<UserFavoriteBiker, String>  {
    List<UserFavoriteBiker> findAllByUid(String uid);

}
