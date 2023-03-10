package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class QuestModifyYoutubeUrlRes {
//    List<UserRegisterQuest> userRegisterQuests;
    @NotNull
    QuestListRes.QuestInfo questInfo;
}
