package com.G19.hospital.repository.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.InventoryRecord;

import java.util.List;
import java.util.Optional;

public interface InventoryRecordRepository extends JpaRepository<InventoryRecord, Long> {
    List<InventoryRecord> findByInventoryItemId(Long inventoryItemId);
    Optional<InventoryRecord> findByInventoryItemIdAndWarehouseId(Long inventoryItemId,
    Long warehouseId);

}
