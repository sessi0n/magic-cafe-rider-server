package com.takeoff.magic_cafe_rider;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class MagicCafeRiderApplication {
    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("DateTime: {}", new Date());
    }
    public static void main(String[] args) {
        SpringApplication.run(MagicCafeRiderApplication.class, args);
    }

}
