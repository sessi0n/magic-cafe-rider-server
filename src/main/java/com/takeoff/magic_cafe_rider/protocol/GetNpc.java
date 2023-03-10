package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Npc;
import com.takeoff.magic_cafe_rider.model.NpcImage;
import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetNpc {
    @NotNull
    Npc npc;

    @NotNull
    User user;

    @NotNull
    List<NpcImage> npcImages;
}
