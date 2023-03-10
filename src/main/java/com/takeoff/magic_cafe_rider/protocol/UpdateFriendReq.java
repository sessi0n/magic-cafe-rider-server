package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateFriendReq {
    String uid;
    String fid;
    boolean unpair;
    boolean pairing;
    boolean accept;
}
