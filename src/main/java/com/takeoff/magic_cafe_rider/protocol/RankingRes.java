package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RankingRes {

    List<Ranker> rankers;

    @Data
    public static class Ranker {
        User user;
        Long rank;
    }
}
