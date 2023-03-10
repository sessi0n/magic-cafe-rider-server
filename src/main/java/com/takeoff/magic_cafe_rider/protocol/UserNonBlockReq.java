package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;


@Data
@EqualsAndHashCode(callSuper=false)
public class UserNonBlockReq {
    @NonNull
    String uid;
    @NonNull
    String targetUid;
}
