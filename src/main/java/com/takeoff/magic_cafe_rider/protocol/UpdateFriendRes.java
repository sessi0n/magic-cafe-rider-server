package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.FriendState;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateFriendRes {
    List<Friend> friendList;

    @Data
    public static class Friend {
        User user;
        FriendState state;
    }
}
