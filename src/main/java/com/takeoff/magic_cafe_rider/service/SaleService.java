package com.takeoff.magic_cafe_rider.service;

import com.takeoff.magic_cafe_rider.model.Sale;
import com.takeoff.magic_cafe_rider.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaleService {
    final private SaleRepository saleRepository;

    public List<Sale> getSaleList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return saleRepository.findAllByDeletedIsFalseOrderByCreatedDesc(pageable);
    }

    public Sale addSale(Sale sale) {
        log.info("2");
        return saleRepository.saveAndFlush(sale);
    }
}
