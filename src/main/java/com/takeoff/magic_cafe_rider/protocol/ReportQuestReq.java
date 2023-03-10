package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class ReportQuestReq {
    @NotNull
    String uid;
    @NotNull
    Long questIdx;
}
