package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.InventoryRecordDto;
import com.G19.hospital.model.inventory.InventoryRecord;
import java.util.List;

public interface InventoryRecordService {
    InventoryRecord createInventoryRecord(InventoryRecordDto recordDto);
    InventoryRecord updateInventoryRecord(Long id, InventoryRecordDto recordDto);
    void deleteInventoryRecord(Long id);
    InventoryRecord getInventoryRecordById(Long id);
    List<InventoryRecord> getInventoryRecordsByItemId(Long inventoryItemId);
    List<InventoryRecord> getAllInventoryRecords();
   InventoryRecord increaseQuantity(Long recordId, int amount);
   /** Decrease the quantity on an existing record by the given amount (not below zero) */
    InventoryRecord decreaseQuantity(Long recordId, int amount);

}
