package com.G19.hospital.controller.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockAdjustmentDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockAdjustmentDto;
import com.G19.hospital.service.inventory.StockAndBatchTracking.StockAdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-adjustments")
@RequiredArgsConstructor
public class StockAdjustmentController {

    private final StockAdjustmentService adjustmentService;

    /** 1. Create a new Stock Adjustment */
    @PostMapping
    public ResponseEntity<StockAdjustmentDto> createAdjustment(
            @Valid @RequestBody CreateStockAdjustmentDto dto) 
    {
        StockAdjustmentDto created = adjustmentService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** 2. Get a Stock Adjustment by ID */
    @GetMapping("/{id}")
    public ResponseEntity<StockAdjustmentDto> getById(@PathVariable Long id) {
        StockAdjustmentDto dto = adjustmentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /** 3. Get all Stock Adjustments (paginated) */
    @GetMapping
    public ResponseEntity<Page<StockAdjustmentDto>> getAll(Pageable pageable) {
        Page<StockAdjustmentDto> page = adjustmentService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    /** 4. Get Stock Adjustments by StockLevel ID (paginated) */
    @GetMapping("/stock-level/{stockLevelId}")
    public ResponseEntity<Page<StockAdjustmentDto>> getByStockLevel(
            @PathVariable Long stockLevelId,
            Pageable pageable) 
    {
        Page<StockAdjustmentDto> page = adjustmentService.getByStockLevel(stockLevelId, pageable);
        return ResponseEntity.ok(page);
    }

    /** 5. Update an existing Stock Adjustment */
    @PutMapping("/{id}")
    public ResponseEntity<StockAdjustmentDto> updateAdjustment(
            @PathVariable Long id,
            @Valid @RequestBody CreateStockAdjustmentDto dto) 
    {
        StockAdjustmentDto updated = adjustmentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** 6. Delete a Stock Adjustment */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdjustment(@PathVariable Long id) {
        adjustmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
