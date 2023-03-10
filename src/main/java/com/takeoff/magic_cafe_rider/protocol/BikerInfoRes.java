package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.QuestImage;
import com.takeoff.magic_cafe_rider.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class BikerInfoRes {
    @NotNull
    User user;

    @NotNull
    List<BikerQuest> bikerQuests;

    @Data
    public static class BikerQuest {
        Quest quest;
        List<QuestImage> questImages;
    }
}
