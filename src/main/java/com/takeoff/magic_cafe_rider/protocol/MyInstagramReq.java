package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MyInstagramReq {
    @NotNull
    private String uid;
    @NotNull
    private String url;

}
