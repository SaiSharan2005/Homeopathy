// src/main/java/com/G19/hospital/service/inventory/StockAndBatchTracking/StockAdjustmentService.java
package com.G19.hospital.service.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockAdjustmentDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockAdjustmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockAdjustmentService {
    StockAdjustmentDto create(CreateStockAdjustmentDto dto);
    StockAdjustmentDto update(Long id, CreateStockAdjustmentDto dto);
    void delete(Long id);
    StockAdjustmentDto getById(Long id);
    Page<StockAdjustmentDto> getAll(Pageable pageable);
    Page<StockAdjustmentDto> getByStockLevel(Long stockLevelId, Pageable pageable);
}
