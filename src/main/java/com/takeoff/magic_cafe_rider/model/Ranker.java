package com.takeoff.magic_cafe_rider.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ranker")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ranker { //for redis return value
    String uid;
    Long rank;
    Double score;
}
