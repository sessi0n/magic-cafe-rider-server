package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class QuestGateChangeReq {
    @NotNull
    private Long qid;

    @NotNull
    private Long srcPid;

    @NotNull
    private Long decPid;
}
