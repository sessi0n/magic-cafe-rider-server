package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.model.CallCenter;
import com.takeoff.magic_cafe_rider.model.CallCenterImage;
import com.takeoff.magic_cafe_rider.repository.CallCenterImageRepository;
import com.takeoff.magic_cafe_rider.repository.CallCenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CallCenterService {
    private final CallCenterRepository callCenterRepository;
    private final CallCenterImageRepository callCenterImageRepository;


    public CallCenter addCs(CallCenter reqCallCenter) {
        return callCenterRepository.saveAndFlush(reqCallCenter);
    }

    public void addCsImages(List<CallCenterImage> callCenterImages) {
        callCenterImageRepository.saveAll(callCenterImages);
    }

    public void updateCs(CallCenter callCenter) {
        callCenterRepository.save(callCenter);
    }
}
