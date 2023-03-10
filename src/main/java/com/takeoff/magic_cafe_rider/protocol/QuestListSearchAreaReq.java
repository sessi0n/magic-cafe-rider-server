package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.SortType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class QuestListSearchAreaReq {
    String uid; //이전 버전과 호환을 위해 NotNull 없앰. ios:1.0.13, and:1.0.13
    @NotNull
    private int pageNum;
    @NotNull
    private int pageSize;
    @NotNull
    private int area;

    private SortType sortType;
}
