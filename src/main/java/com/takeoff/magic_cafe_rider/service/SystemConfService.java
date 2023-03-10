package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.model.Conf;
import com.takeoff.magic_cafe_rider.repository.SystemConfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SystemConfService {
    private final SystemConfRepository systemConfRepository;


    public String getVersion(String key) {
        Conf conf = systemConfRepository.findByKey(key);
        return conf.getValue();
    }
}
