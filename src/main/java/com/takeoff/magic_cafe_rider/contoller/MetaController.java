package com.takeoff.magic_cafe_rider.contoller;

import com.takeoff.magic_cafe_rider.model.Npc;
import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.service.NpcService;
import com.takeoff.magic_cafe_rider.service.QuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/meta")
@RequiredArgsConstructor
public class MetaController {
    @Value("${map.kakaoKey}")
    private String kakaoKey;
    private final NpcService npcService;
    private final QuestService questService;

    @GetMapping("/privacy")
    public String Privacy() {

        return "meta/clause";
    }

    @GetMapping("/info")
    public String Info() {

        return "meta/index";
    }
}
