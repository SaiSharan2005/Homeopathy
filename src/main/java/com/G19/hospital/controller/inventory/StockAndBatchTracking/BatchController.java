package com.G19.hospital.controller.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.BatchDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateBatchDto;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;
import com.G19.hospital.service.inventory.StockAndBatchTracking.BatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    /** 1. Create a new Batch */
    @PostMapping
    public ResponseEntity<BatchDto> create(@Valid @RequestBody CreateBatchDto dto) {
        BatchDto created = batchService.createBatch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 2. Get a Batch by its ID */
    @GetMapping("/{id}")
    public ResponseEntity<BatchDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    /** 3. Get all Batches (paginated) */
    @GetMapping
    public ResponseEntity<Page<BatchDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(batchService.getAllBatches(pageable));
    }

    /** 4. Get Batches by Inventory Item ID */
    @GetMapping("/inventory/{inventoryItemId}")
    public ResponseEntity<Page<BatchDto>> getByInventory(
            @PathVariable Long inventoryItemId,
            Pageable pageable) 
    {
        return ResponseEntity.ok(
            batchService.getBatchesByInventoryItem(inventoryItemId, pageable));
    }

    /** 5. Get Batches by Status */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<BatchDto>> getByStatus(
            @PathVariable BatchStatus status,
            Pageable pageable) 
    {
        return ResponseEntity.ok(
            batchService.getBatchesByStatus(status, pageable));
    }

    /** 6. Get Batches by Inventory Item and Status */
    @GetMapping("/inventory/{inventoryItemId}/status/{status}")
    public ResponseEntity<Page<BatchDto>> getByInventoryAndStatus(
            @PathVariable Long inventoryItemId,
            @PathVariable BatchStatus status,
            Pageable pageable) 
    {
        return ResponseEntity.ok(
            batchService.getBatchesByInventoryItemAndStatus(inventoryItemId, status, pageable));
    }

    /** 7. Update an existing Batch */
    @PutMapping("/{id}")
    public ResponseEntity<BatchDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateBatchDto dto) 
    {
        return ResponseEntity.ok(batchService.updateBatch(id, dto));
    }

    /** 8. Delete a Batch */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }
}
