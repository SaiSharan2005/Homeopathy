package com.G19.hospital.controller.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockLevelDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockLevelDto;
import com.G19.hospital.service.inventory.StockAndBatchTracking.StockLevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-levels")
@RequiredArgsConstructor
public class StockLevelController {

    private final StockLevelService stockLevelService;

    /** 1. Create a new StockLevel */
    @PostMapping
    public ResponseEntity<StockLevelDto> createStockLevel(
            @Valid @RequestBody CreateStockLevelDto dto) 
    {
        StockLevelDto created = stockLevelService.createStockLevel(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** 2. Get a StockLevel by ID */
    @GetMapping("/{id}")
    public ResponseEntity<StockLevelDto> getStockLevelById(
            @PathVariable Long id) 
    {
        StockLevelDto dto = stockLevelService.getStockLevelById(id);
        return ResponseEntity.ok(dto);
    }

    /** 3. Get all StockLevels (paginated) */
    @GetMapping
    public ResponseEntity<Page<StockLevelDto>> getAllStockLevels(
            Pageable pageable) 
    {
        Page<StockLevelDto> page = stockLevelService.getAllStockLevels(pageable);
        return ResponseEntity.ok(page);
    }

    /** 4. Get StockLevels by Batch ID (paginated) */
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<Page<StockLevelDto>> getByBatch(
            @PathVariable Long batchId,
            Pageable pageable) 
    {
        Page<StockLevelDto> page = stockLevelService.getStockLevelsByBatch(batchId, pageable);
        return ResponseEntity.ok(page);
    }

    /** 5. Get StockLevels by Warehouse ID (paginated) */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<Page<StockLevelDto>> getByWarehouse(
            @PathVariable Long warehouseId,
            Pageable pageable) 
    {
        Page<StockLevelDto> page = stockLevelService.getStockLevelsByWarehouse(warehouseId, pageable);
        return ResponseEntity.ok(page);
    }

    /** 6. Update an existing StockLevel */
    @PutMapping("/{id}")
    public ResponseEntity<StockLevelDto> updateStockLevel(
            @PathVariable Long id,
            @Valid @RequestBody CreateStockLevelDto dto) 
    {
        StockLevelDto updated = stockLevelService.updateStockLevel(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** 7. Delete a StockLevel */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockLevel(
            @PathVariable Long id) 
    {
        stockLevelService.deleteStockLevel(id);
        return ResponseEntity.noContent().build();
    }
}
