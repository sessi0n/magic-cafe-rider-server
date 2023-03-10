package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class GetAgit {
    List<AgitInfo> agitInfos;

    @Data
    public static class AgitInfo {
        Long qid;
        String name;
    }
}
