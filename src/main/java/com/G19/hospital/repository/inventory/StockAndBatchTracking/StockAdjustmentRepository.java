// src/main/java/com/G19/hospital/repository/inventory/StockAndBatchTracking/StockAdjustmentRepository.java
package com.G19.hospital.repository.inventory.StockAndBatchTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.StockAndBatchTracking.StockAdjustment;

public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, Long> {
    Page<StockAdjustment> findAll(Pageable pageable);
    Page<StockAdjustment> findByStockLevel_Id(Long stockLevelId, Pageable pageable);
}
