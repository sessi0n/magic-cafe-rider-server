package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.NpcType;
import com.takeoff.magic_cafe_rider.enums.QuestType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class NpcListSearchJobReq {
    @NotNull
    private int pageNum;
    @NotNull
    private int pageSize;
    @NotNull
    private NpcType npcType;
}
