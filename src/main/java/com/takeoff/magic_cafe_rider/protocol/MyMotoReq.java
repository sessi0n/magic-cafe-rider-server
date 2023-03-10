package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MyMotoReq {
    @NotNull
    private String uid;

    @NotNull
    private int bikeBrand;
    @NotNull
    private int bikeModel;
}
