package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String>  {
    Owner findByUidAndDeletedIsFalse(String uid);
    Owner findByQidAndDeletedIsFalse(Long qid);
}
