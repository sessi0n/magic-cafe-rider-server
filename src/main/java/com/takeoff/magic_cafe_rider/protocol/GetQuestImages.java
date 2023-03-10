package com.takeoff.magic_cafe_rider.protocol;

import com.takeoff.magic_cafe_rider.model.QuestImage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetQuestImages {
    List<QuestImage> questImages;
}
