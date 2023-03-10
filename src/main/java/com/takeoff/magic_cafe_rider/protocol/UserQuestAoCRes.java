package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.UserQuest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserQuestAoCRes {
    int retCode; //0: accept 1:cancel
    Quest quest;
    List<UserQuest> acceptQuests;
}
