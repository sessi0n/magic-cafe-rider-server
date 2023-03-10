package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.QuestImage;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class QuestListRes {
    @Data
    public static class QuestInfo {
        Quest quest;
        List<QuestImage> questImages;
        User user;
    }

    @NotNull
    List<QuestInfo> questInfos;
}
