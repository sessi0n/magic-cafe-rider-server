package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.enums.MarkerType;
import com.takeoff.magic_cafe_rider.model.Npc;
import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarkerRes {
    @NotNull
    List<MarkerInfo> markerInfos;

    @Data
    public static class MarkerInfo {
        MarkerType type;
        Long id;
        String title;
        double lat;
        double lng;
    }
}
