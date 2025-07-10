// src/main/java/com/G19/hospital/service/implement/inventory/StockAndBatchTracking/StockLevelServiceImpl.java
package com.G19.hospital.service.implement.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockLevelDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockLevelDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.StockAndBatchTracking.StockLevel;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.StockLevelRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.BatchRepository;
import com.G19.hospital.repository.inventory.core.WarehouseRepository;
import com.G19.hospital.service.inventory.StockAndBatchTracking.StockLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockLevelServiceImpl implements StockLevelService {

    private final StockLevelRepository stockLevelRepo;
    private final BatchRepository batchRepo;
    private final WarehouseRepository warehouseRepo;

    @Override
    public StockLevelDto createStockLevel(CreateStockLevelDto dto) {
        var batch = batchRepo.findById(dto.getBatchId())
            .orElseThrow(() -> new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND));
        var warehouse = warehouseRepo.findById(dto.getWarehouseId())
            .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));

        StockLevel sl = new StockLevel();
        sl.setBatch(batch);
        sl.setWarehouse(warehouse);
        sl.setQuantityOnHand(dto.getQuantityOnHand());
        sl.setReservedQuantity(dto.getReservedQuantity());

        var saved = stockLevelRepo.save(sl);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StockLevelDto getStockLevelById(Long id) {
        return stockLevelRepo.findById(id)
            .map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockLevelDto> getAllStockLevels(Pageable pageable) {
        return stockLevelRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockLevelDto> getStockLevelsByBatch(Long batchId, Pageable pageable) {
        // validate batch
        batchRepo.findById(batchId).orElseThrow(() ->
            new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND));
        return stockLevelRepo.findByBatch_Id(batchId, pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockLevelDto> getStockLevelsByWarehouse(Long warehouseId, Pageable pageable) {
        warehouseRepo.findById(warehouseId).orElseThrow(() ->
            new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));
        return stockLevelRepo.findByWarehouse_Id(warehouseId, pageable).map(this::toDto);
    }

    @Override
    public StockLevelDto updateStockLevel(Long id, CreateStockLevelDto dto) {
        StockLevel sl = stockLevelRepo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND));

        if (dto.getBatchId() != null && !dto.getBatchId().equals(sl.getBatch().getId())) {
            sl.setBatch(batchRepo.findById(dto.getBatchId())
                .orElseThrow(() -> new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND)));
        }
        if (dto.getWarehouseId() != null && !dto.getWarehouseId().equals(sl.getWarehouse().getId())) {
            sl.setWarehouse(warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND)));
        }
        sl.setQuantityOnHand(dto.getQuantityOnHand());
        sl.setReservedQuantity(dto.getReservedQuantity());

        var updated = stockLevelRepo.save(sl);
        return toDto(updated);
    }

    @Override
    public void deleteStockLevel(Long id) {
        if (!stockLevelRepo.existsById(id)) {
            throw new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND);
        }
        stockLevelRepo.deleteById(id);
    }

    private StockLevelDto toDto(StockLevel sl) {
        return new StockLevelDto(
            sl.getId(),
            sl.getBatch().getId(),
            sl.getWarehouse().getId(),
            sl.getQuantityOnHand(),
            sl.getReservedQuantity()
        );
    }
}
