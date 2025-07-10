// src/main/java/com/G19/hospital/service/inventory/StockAndBatchTracking/StockLevelService.java
package com.G19.hospital.service.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockLevelDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockLevelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockLevelService {
    StockLevelDto createStockLevel(CreateStockLevelDto dto);
    StockLevelDto getStockLevelById(Long id);
    Page<StockLevelDto> getAllStockLevels(Pageable pageable);
    Page<StockLevelDto> getStockLevelsByBatch(Long batchId, Pageable pageable);
    Page<StockLevelDto> getStockLevelsByWarehouse(Long warehouseId, Pageable pageable);
    StockLevelDto updateStockLevel(Long id, CreateStockLevelDto dto);
    void deleteStockLevel(Long id);
}
