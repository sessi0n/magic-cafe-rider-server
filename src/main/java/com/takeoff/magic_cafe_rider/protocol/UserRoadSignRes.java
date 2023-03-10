package com.takeoff.magic_cafe_rider.protocol;


import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper=false)
public class UserRoadSignRes {
    @NotNull User user;
}
