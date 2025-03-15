package com.G19.hospital.controller.inventory;

import com.G19.hospital.DTO.inventory.InventoryRecordDto;
import com.G19.hospital.model.inventory.InventoryRecord;
import com.G19.hospital.service.inventory.InventoryRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-records")
@Slf4j
public class InventoryRecordController {

    @Autowired
    private InventoryRecordService inventoryRecordService;

    @PostMapping
    public ResponseEntity<InventoryRecord> createInventoryRecord(@RequestBody InventoryRecordDto recordDto) {
        InventoryRecord record = inventoryRecordService.createInventoryRecord(recordDto);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryRecord> updateInventoryRecord(@PathVariable Long id, @RequestBody InventoryRecordDto recordDto) {
        InventoryRecord record = inventoryRecordService.updateInventoryRecord(id, recordDto);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryRecord(@PathVariable Long id) {
        inventoryRecordService.deleteInventoryRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryRecord> getInventoryRecordById(@PathVariable Long id) {
        InventoryRecord record = inventoryRecordService.getInventoryRecordById(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/item/{inventoryItemId}")
    public ResponseEntity<List<InventoryRecord>> getRecordsByInventoryItemId(@PathVariable Long inventoryItemId) {
        List<InventoryRecord> records = inventoryRecordService.getInventoryRecordsByItemId(inventoryItemId);
        return ResponseEntity.ok(records);
    }

    @GetMapping
    public ResponseEntity<List<InventoryRecord>> getAllInventoryRecords() {
        List<InventoryRecord> records = inventoryRecordService.getAllInventoryRecords();
        return ResponseEntity.ok(records);
    }
}
