package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Npc;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class NpcModifyReq {
    List<Long> delImages;
    Npc npc;
}
