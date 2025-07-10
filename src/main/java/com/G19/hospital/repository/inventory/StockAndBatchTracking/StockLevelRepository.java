// src/main/java/com/G19/hospital/repository/inventory/StockAndBatchTracking/StockLevelRepository.java
package com.G19.hospital.repository.inventory.StockAndBatchTracking;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.StockAndBatchTracking.StockLevel;

public interface StockLevelRepository extends JpaRepository<StockLevel, Long> {
    Page<StockLevel> findAll(Pageable pageable);
    Page<StockLevel> findByBatch_Id(Long batchId, Pageable pageable);
    Page<StockLevel> findByWarehouse_Id(Long warehouseId, Pageable pageable);
     Optional<StockLevel> findFirstByBatch_InventoryItem_IdAndWarehouse_IdOrderByBatch_ExpiryDateDesc(
    Long inventoryItemId,
    Long warehouseId
    );
    // StockLevelRepository.java (add)
Optional<StockLevel> findFirstByBatch_IdAndWarehouse_Id(Long batchId, Long warehouseId);

}
