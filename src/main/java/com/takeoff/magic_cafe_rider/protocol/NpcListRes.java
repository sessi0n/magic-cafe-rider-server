package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NpcListRes {
    @Data
    public static class NpcInfo {
        Npc npc;
        List<NpcImage> npcImages;
        User user;
    }

    @NotNull
    List<NpcInfo> npcInfos;
}
