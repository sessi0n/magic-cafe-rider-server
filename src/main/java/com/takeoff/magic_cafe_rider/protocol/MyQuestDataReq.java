package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.QuestDataReqType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MyQuestDataReq {
    @NotNull
    private String uid;

    @NotNull
    private QuestDataReqType type;
}
