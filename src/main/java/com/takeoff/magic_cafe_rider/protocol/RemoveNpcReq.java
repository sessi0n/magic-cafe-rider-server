package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class RemoveNpcReq {
    @NotNull
    String uid;
    @NotNull
    Long npcIdx;
}
