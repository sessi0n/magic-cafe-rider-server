package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MarkerReq {
    @NotNull
    double lat;

    @NotNull
    double lng;
}
