package com.takeoff.magic_cafe_rider.repository;


import com.takeoff.magic_cafe_rider.model.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserBlockRepository extends JpaRepository<UserBlock, String>  {

    List<UserBlock> findAllByUid(String uid);

}
