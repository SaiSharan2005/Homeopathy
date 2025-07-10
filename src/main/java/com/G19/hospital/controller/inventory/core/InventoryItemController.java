package com.G19.hospital.controller.inventory.core;

import com.G19.hospital.DTO.inventory.InventoryItemDto;
import com.G19.hospital.model.inventory.core.InventoryItem;
import com.G19.hospital.service.inventory.core.InventoryItemService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/inventory-items")
@Slf4j
public class InventoryItemController {

    @Autowired
    private InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(
            @RequestPart("inventoryItem") InventoryItemDto inventoryItemDto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        InventoryItem item = inventoryItemService.createInventoryItem(inventoryItemDto, image);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(
            @PathVariable Long id,
            @RequestPart("inventoryItem") InventoryItemDto inventoryItemDto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        InventoryItem item = inventoryItemService.updateInventoryItem(id, inventoryItemDto, image);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long id) {
        InventoryItem item = inventoryItemService.getInventoryItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(items);
    }

    // Endpoint to update stock for a given inventory item
    @PatchMapping("/{id}/stock")
    public ResponseEntity<InventoryItem> updateStock(@PathVariable Long id, @RequestParam int change) {
        InventoryItem item = inventoryItemService.updateStock(id, change);
        return ResponseEntity.ok(item);
    }
}
