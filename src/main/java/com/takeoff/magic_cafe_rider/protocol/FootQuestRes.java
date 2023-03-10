package com.takeoff.magic_cafe_rider.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FootQuestRes {
    boolean isSuccess;
    int score;

    @JsonProperty(value="isSuccess")
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
