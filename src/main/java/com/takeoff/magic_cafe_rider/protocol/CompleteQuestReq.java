package com.takeoff.magic_cafe_rider.protocol;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class CompleteQuestReq {
    @NotNull
    private String uid;

    @NotNull
    private Long qid;
}
