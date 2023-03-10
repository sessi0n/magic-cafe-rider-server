package com.takeoff.magic_cafe_rider.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.takeoff.magic_cafe_rider.model.Quest;
import com.takeoff.magic_cafe_rider.model.Sale;
import com.takeoff.magic_cafe_rider.model.User;
import com.takeoff.magic_cafe_rider.protocol.*;
import com.takeoff.magic_cafe_rider.service.SaleService;
import com.takeoff.magic_cafe_rider.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/sale")
public class SaleController {
    final private SaleService saleService;
    private final UserService userService;

    @PostMapping(value = "/list")
    public SaleListRes function(@RequestBody @Valid SaleListReq req, SaleListRes res) {
        List<Sale> sales = saleService.getSaleList(req.getPageNum(), req.getPageSize());

        List<SaleListRes.SaleInfo> saleInfos = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        sales.forEach(q-> {
            if (now.compareTo(q.getEnd()) > 0) {
                return;
            }
            SaleListRes.SaleInfo saleInfo = new SaleListRes.SaleInfo();
            saleInfo.setSale(q);
            User user = userService.getUserData(q.getUid());
            saleInfo.setUser(user);

            saleInfos.add(saleInfo);
        });

        res.setSaleInfos(saleInfos);
        return res;
    }

    @PostMapping(value = "/add")
    public SaleAddRes function(@RequestBody @Valid SaleAddReq req, SaleAddRes res) {
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new SimpleModule());
//        Sale reqQuest = objectMapper.readValue(req.getSale(), new TypeReference<>() {});

        log.info("1");
        Sale sale = saleService.addSale(req.getSale());

        res.setSale(sale);
        return res;
    }
}
