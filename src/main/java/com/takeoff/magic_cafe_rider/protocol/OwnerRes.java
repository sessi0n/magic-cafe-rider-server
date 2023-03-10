package com.takeoff.magic_cafe_rider.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OwnerRes {
    @NotNull
    boolean isSuccess; //1 : add Success 2: delete Success

    @JsonProperty(value="isSuccess")
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
