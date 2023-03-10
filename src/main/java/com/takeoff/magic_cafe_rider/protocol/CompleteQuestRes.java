package com.takeoff.magic_cafe_rider.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeoff.magic_cafe_rider.model.UserQuest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompleteQuestRes {
    boolean isSuccess;
    List<UserQuest> myQuests;
    int score;

    @JsonProperty(value="isSuccess")
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
