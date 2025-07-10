// src/main/java/com/G19/hospital/repository/inventory/StockAndBatchTracking/BatchRepository.java
package com.G19.hospital.repository.inventory.StockAndBatchTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    Page<Batch> findAll(Pageable pageable);
    Page<Batch> findByInventoryItem_Id(Long inventoryItemId, Pageable pageable);
    Page<Batch> findByStatus(BatchStatus status, Pageable pageable);
    Page<Batch> findByInventoryItem_IdAndStatus(Long inventoryItemId, BatchStatus status, Pageable pageable);
}
