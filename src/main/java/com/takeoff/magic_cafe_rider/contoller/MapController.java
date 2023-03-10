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
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    @Value("${map.kakaoKey}")
    private String kakaoKey;
    private final NpcService npcService;
    private final QuestService questService;

    @GetMapping("/quest/{qid}")
    public String QuestDetail(@PathVariable Long qid, Model model) {
        Quest quest = questService.getQuest(qid);

        model.addAttribute("kakakoKey", kakaoKey);
        model.addAttribute("myShopLat", quest.getLat());
        model.addAttribute("myShopLng", quest.getLng());

        return "views/Detail";
    }

    @GetMapping("/npc/{nid}")
    public String NpcDetail(@PathVariable Long nid, Model model) {
        Npc npc = npcService.getNpc(nid);

        model.addAttribute("kakakoKey", kakaoKey);
        model.addAttribute("myShopLat", npc.getLat());
        model.addAttribute("myShopLng", npc.getLng());

        return "views/Detail";
    }

    @GetMapping("/group/{lat}/{lng}")
    public String Group(@PathVariable double lat, @PathVariable double lng, Model model) {
        model.addAttribute("kakakoKey", kakaoKey);
        model.addAttribute("myLat", lat);
        model.addAttribute("myLng", lng);

        return "views/Group";
    }

    @GetMapping("/all/{lat}/{lng}")
    public String All(@PathVariable double lat, @PathVariable double lng, Model model) {
        log.info("All: {}, {}", lat, lng);
        model.addAttribute("kakakoKey", kakaoKey);
        model.addAttribute("myLat", lat);
        model.addAttribute("myLng", lng);

        return "views/All";
    }
}
