package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.Quest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class QuestModifyReq {
    List<Long> delImages;
    Quest quest;
}
