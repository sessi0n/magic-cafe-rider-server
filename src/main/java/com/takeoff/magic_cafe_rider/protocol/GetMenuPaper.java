package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Owner;
import com.takeoff.magic_cafe_rider.model.QuestMenuPaper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetMenuPaper {
    @NotNull
    QuestMenuPaper menuPaper;
}
