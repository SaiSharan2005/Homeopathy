package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.InventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRecordRepository extends JpaRepository<InventoryRecord, Long> {
    List<InventoryRecord> findByInventoryItemId(Long inventoryItemId);
}
