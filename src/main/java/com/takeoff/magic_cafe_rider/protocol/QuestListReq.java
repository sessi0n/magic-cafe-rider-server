package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.QuestType;
import com.takeoff.magic_cafe_rider.enums.SearchType;
import com.takeoff.magic_cafe_rider.enums.SortType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class QuestListReq {
    String uid; //이전 버전과 호환을 위해 NotNull 없앰. ios:1.0.13, and:1.0.13

    @NotNull
    private int pageNum;
    @NotNull
    private int pageSize;
    @NotNull
    private SearchType searchType;

    private int name;
    private int area;
    private QuestType questType;
    private SortType sortType;
}
