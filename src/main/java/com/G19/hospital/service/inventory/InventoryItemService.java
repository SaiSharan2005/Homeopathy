package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.InventoryItemDto;
import com.G19.hospital.model.inventory.InventoryItem;
import java.util.List;

public interface InventoryItemService {
    InventoryItem createInventoryItem(InventoryItemDto inventoryItemDto);
    InventoryItem updateInventoryItem(Long id, InventoryItemDto inventoryItemDto);
    void deleteInventoryItem(Long id);
    InventoryItem getInventoryItemById(Long id);
    List<InventoryItem> getAllInventoryItems();
    InventoryItem updateStock(Long id, int change);
}
