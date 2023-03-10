package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class NpcModifyYoutubeUrlReq {
    Long id;
    String uid;
    String url;
}
