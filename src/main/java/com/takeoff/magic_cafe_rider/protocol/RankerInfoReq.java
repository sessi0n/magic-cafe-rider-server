package com.takeoff.magic_cafe_rider.protocol;

import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class RankerInfoReq {
    @NotNull
    private String uid;

}
