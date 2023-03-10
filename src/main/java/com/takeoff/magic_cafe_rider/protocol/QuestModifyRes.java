package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.UserRegisterQuest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class QuestModifyRes {
//    List<UserRegisterQuest> userRegisterQuests;
    @NotNull
    QuestListRes.QuestInfo questInfo;
}
