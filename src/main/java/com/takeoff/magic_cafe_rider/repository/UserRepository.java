package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    User findByUid(String uid);
    List<User> findAllByEmail(String email);
    List<User> findByUidIn(List<String> uids);
    List<User> findTop20ByOrderByScoreDesc();
}
