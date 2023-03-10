package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Conf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfRepository extends JpaRepository<Conf, String> {
    Conf findByKey(String key);
}
