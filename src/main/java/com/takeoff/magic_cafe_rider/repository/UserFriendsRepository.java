package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserFriendsRepository extends JpaRepository<UserFriend, String>  {
    Optional<UserFriend> findByUidAndFid(String uid, String fid);
    List<UserFriend> findAllByUidOrderByDateDesc(String uid);
    void deleteAllByFid(String fid);
    Optional<UserFriend> findTopByUidAndFidOrderByDateDesc(String uid, String fid);
}
