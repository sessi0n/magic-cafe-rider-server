package com.takeoff.magic_cafe_rider.protocol;


import lombok.Data;

import jakarta.validation.constraints.NotNull;


@Data
public class UserRoadSignReq {
    @NotNull
    String uid;
}
