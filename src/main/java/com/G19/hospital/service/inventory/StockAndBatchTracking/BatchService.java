// src/main/java/com/G19/hospital/service/inventory/StockAndBatchTracking/BatchService.java
package com.G19.hospital.service.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.BatchDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateBatchDto;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BatchService {
    BatchDto createBatch(CreateBatchDto dto);
    BatchDto getBatchById(Long batchId);
    Page<BatchDto> getAllBatches(Pageable pageable);
    Page<BatchDto> getBatchesByInventoryItem(Long inventoryItemId, Pageable pageable);
    Page<BatchDto> getBatchesByStatus(BatchStatus status, Pageable pageable);
    Page<BatchDto> getBatchesByInventoryItemAndStatus(Long inventoryItemId, BatchStatus status, Pageable pageable);
    BatchDto updateBatch(Long batchId, CreateBatchDto dto);
    void deleteBatch(Long batchId);
}
