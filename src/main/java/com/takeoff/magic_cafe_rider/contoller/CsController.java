package com.takeoff.magic_cafe_rider.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeoff.magic_cafe_rider.manager.FileManager;
import com.takeoff.magic_cafe_rider.model.CallCenter;
import com.takeoff.magic_cafe_rider.model.CallCenterImage;
import com.takeoff.magic_cafe_rider.protocol.CsAddRes;
import com.takeoff.magic_cafe_rider.service.CallCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/cs")
public class CsController {
    private final FileManager fileManager;
    private final CallCenterService callCenterService;

    @PostMapping(value = "/add")
    public CsAddRes function(@RequestPart(value="files", required=false)  List<MultipartFile> files, @RequestParam("cs") String strCs, CsAddRes res) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new SimpleModule());
        CallCenter reqCallCenter = objectMapper.readValue(strCs, new TypeReference<>() {});

        CallCenter callCenter = callCenterService.addCs(reqCallCenter);

        List<CallCenterImage> callCenterImages = new ArrayList<>();
        String dbPath = fileManager.makeCsPath();

        if (files != null) {
            for (MultipartFile f : files) {
                if (f.getSize() <= 0) {
                    log.debug("push file error: {}", f.getOriginalFilename());
                    continue;
                }

                log.info("push file : {}", f.getOriginalFilename());
                String fileName = fileManager.pushCommonFile(f, dbPath);

                CallCenterImage callCenterImage = CallCenterImage.builder()
                        .csid(callCenter.getCsid())
                        .imgUrl(dbPath)
                        .imgFile(fileName)
                        .build();
                callCenterImages.add(callCenterImage);
            }
        }

        callCenterService.addCsImages(callCenterImages);

        res.setSuccess(true);

        return res;
    }

}
