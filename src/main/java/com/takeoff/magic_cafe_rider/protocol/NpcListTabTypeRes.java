package com.takeoff.magic_cafe_rider.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NpcListTabTypeRes {

    @NotNull
    List<NpcListRes.NpcInfo> npcInfos;
}
