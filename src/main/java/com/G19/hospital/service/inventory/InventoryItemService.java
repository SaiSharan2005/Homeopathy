package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.InventoryItemDto;
import com.G19.hospital.model.inventory.InventoryItem;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface InventoryItemService {
    // Method to upload image and return its URL
    String uploadImage(MultipartFile imageFile) throws IOException;

    // Modified methods to accept an image file
    InventoryItem createInventoryItem(InventoryItemDto inventoryItemDto, MultipartFile image) throws IOException;
    InventoryItem updateInventoryItem(Long id, InventoryItemDto inventoryItemDto, MultipartFile image) throws IOException;

    void deleteInventoryItem(Long id);
    InventoryItem getInventoryItemById(Long id);
    List<InventoryItem> getAllInventoryItems();
    InventoryItem updateStock(Long id, int change);
}
