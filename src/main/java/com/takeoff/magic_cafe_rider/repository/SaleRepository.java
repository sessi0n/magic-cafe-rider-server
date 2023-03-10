package com.takeoff.magic_cafe_rider.repository;

import com.takeoff.magic_cafe_rider.model.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    List<Sale> findAllByDeletedIsFalseOrderByCreatedDesc(Pageable pageable);
}
