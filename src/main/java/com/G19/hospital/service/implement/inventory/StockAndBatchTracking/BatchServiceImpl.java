// src/main/java/com/G19/hospital/service/implement/inventory/StockAndBatchTracking/BatchServiceImpl.java
package com.G19.hospital.service.implement.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.BatchDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateBatchDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch.BatchStatus;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.BatchRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.service.inventory.StockAndBatchTracking.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepo;
    private final InventoryItemRepository itemRepo;

    @Override
    public BatchDto createBatch(CreateBatchDto dto) {
        var inv = itemRepo.findById(dto.getInventoryItemId())
            .orElseThrow(() -> new CustomSecurityException(
                "InventoryItem not found", HttpStatus.NOT_FOUND));

        Batch b = new Batch();
        b.setInventoryItem(inv);
        b.setBatchNumber(dto.getBatchNumber());
        b.setExpiryDate(dto.getExpiryDate());
        b.setStatus(dto.getStatus() != null ? dto.getStatus() : BatchStatus.ACTIVE);

        Batch saved = batchRepo.save(b);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BatchDto getBatchById(Long batchId) {
        return batchRepo.findById(batchId)
            .map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException(
                "Batch not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchDto> getAllBatches(Pageable pageable) {
        return batchRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchDto> getBatchesByInventoryItem(Long inventoryItemId, Pageable pageable) {
        // validate inventoryItem existence
        itemRepo.findById(inventoryItemId).orElseThrow(() ->
            new CustomSecurityException("InventoryItem not found", HttpStatus.NOT_FOUND));
        return batchRepo.findByInventoryItem_Id(inventoryItemId, pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchDto> getBatchesByStatus(BatchStatus status, Pageable pageable) {
        return batchRepo.findByStatus(status, pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BatchDto> getBatchesByInventoryItemAndStatus(Long inventoryItemId, BatchStatus status, Pageable pageable) {
        itemRepo.findById(inventoryItemId).orElseThrow(() ->
            new CustomSecurityException("InventoryItem not found", HttpStatus.NOT_FOUND));
        return batchRepo.findByInventoryItem_IdAndStatus(inventoryItemId, status, pageable)
                        .map(this::toDto);
    }

    @Override
    public BatchDto updateBatch(Long batchId, CreateBatchDto dto) {
        Batch b = batchRepo.findById(batchId)
            .orElseThrow(() -> new CustomSecurityException(
                "Batch not found", HttpStatus.NOT_FOUND));

        if (dto.getInventoryItemId() != null) {
            var inv = itemRepo.findById(dto.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException(
                    "InventoryItem not found", HttpStatus.NOT_FOUND));
            b.setInventoryItem(inv);
        }
        b.setBatchNumber(dto.getBatchNumber());
        b.setExpiryDate(dto.getExpiryDate());
        if (dto.getStatus() != null) b.setStatus(dto.getStatus());

        Batch updated = batchRepo.save(b);
        return toDto(updated);
    }

    @Override
    public void deleteBatch(Long batchId) {
        if (!batchRepo.existsById(batchId)) {
            throw new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND);
        }
        batchRepo.deleteById(batchId);
    }

    private BatchDto toDto(Batch b) {
        return new BatchDto(
            b.getId(),
            b.getInventoryItem().getId(),
            b.getBatchNumber(),
            b.getExpiryDate(),
            b.getStatus()
        );
    }
}
