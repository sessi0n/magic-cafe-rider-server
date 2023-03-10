package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class ReviewAddReq {
    Long qid;
    String uid;
    String context;
}
