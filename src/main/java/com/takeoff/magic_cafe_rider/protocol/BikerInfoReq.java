package com.takeoff.magic_cafe_rider.protocol;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BikerInfoReq {
    @NotNull
    private String uid;

}
