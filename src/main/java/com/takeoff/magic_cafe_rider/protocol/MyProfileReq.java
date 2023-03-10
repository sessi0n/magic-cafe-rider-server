package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.ServiceType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MyProfileReq {
    @NotNull
    private String uid;
    @NotNull
    private String email;

    private String nick;
    private String avatar;
    private ServiceType service;
}
