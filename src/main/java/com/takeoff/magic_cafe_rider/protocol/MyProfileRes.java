package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MyProfileRes {
    @NotNull
    User user;
    @NotNull
    List<UserRegisterQuest> registerQuests;
    @NotNull
    List<UserQuest> myQuests;
    @NotNull
    List<User> favoriteBikers;
    @NotNull
    List<UserRegisterNpc> registerNpcs;
}
