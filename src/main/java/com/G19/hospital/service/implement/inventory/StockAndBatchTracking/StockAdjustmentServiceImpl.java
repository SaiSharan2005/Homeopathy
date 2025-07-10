// src/main/java/com/G19/hospital/service/implement/inventory/StockAndBatchTracking/StockAdjustmentServiceImpl.java
package com.G19.hospital.service.implement.inventory.StockAndBatchTracking;

import com.G19.hospital.DTO.inventory.StockAndBatchTracking.CreateStockAdjustmentDto;
import com.G19.hospital.DTO.inventory.StockAndBatchTracking.StockAdjustmentDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.StockAndBatchTracking.StockAdjustment;
import com.G19.hospital.model.inventory.StockAndBatchTracking.StockLevel;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.StockAdjustmentRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.StockLevelRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.inventory.StockAndBatchTracking.StockAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

    private final StockAdjustmentRepository adjRepo;
    private final StockLevelRepository levelRepo;
    private final UserRepository userRepo;

    @Override
    public StockAdjustmentDto create(CreateStockAdjustmentDto dto) {
        StockLevel level = levelRepo.findById(dto.getStockLevelId())
            .orElseThrow(() -> new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND));
        User user = userRepo.findById(dto.getAdjustedById())
            .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));

        StockAdjustment adj = new StockAdjustment();
        adj.setStockLevel(level);
        adj.setAdjustedBy(user);
        adj.setAdjDate(dto.getAdjDate() != null ? dto.getAdjDate() : LocalDateTime.now());
        adj.setAdjType(dto.getAdjType());
        adj.setQuantity(dto.getQuantity());

        StockAdjustment saved = adjRepo.save(adj);
        return toDto(saved);
    }

    @Override
    public StockAdjustmentDto update(Long id, CreateStockAdjustmentDto dto) {
        StockAdjustment adj = adjRepo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("Adjustment not found", HttpStatus.NOT_FOUND));

        if (dto.getStockLevelId() != null && !dto.getStockLevelId().equals(adj.getStockLevel().getId())) {
            StockLevel level = levelRepo.findById(dto.getStockLevelId())
                .orElseThrow(() -> new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND));
            adj.setStockLevel(level);
        }
        if (dto.getAdjustedById() != null && !dto.getAdjustedById().equals(adj.getAdjustedBy().getId())) {
            User u = userRepo.findById(dto.getAdjustedById())
                .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));
            adj.setAdjustedBy(u);
        }
        adj.setAdjDate(dto.getAdjDate() != null ? dto.getAdjDate() : adj.getAdjDate());
        adj.setAdjType(dto.getAdjType());
        adj.setQuantity(dto.getQuantity());

        return toDto(adjRepo.save(adj));
    }

    @Override
    public void delete(Long id) {
        if (!adjRepo.existsById(id)) {
            throw new CustomSecurityException("Adjustment not found", HttpStatus.NOT_FOUND);
        }
        adjRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StockAdjustmentDto getById(Long id) {
        return adjRepo.findById(id).map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException("Adjustment not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockAdjustmentDto> getAll(Pageable pageable) {
        return adjRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockAdjustmentDto> getByStockLevel(Long stockLevelId, Pageable pageable) {
        // validate
        levelRepo.findById(stockLevelId).orElseThrow(() ->
            new CustomSecurityException("StockLevel not found", HttpStatus.NOT_FOUND));
        return adjRepo.findByStockLevel_Id(stockLevelId, pageable).map(this::toDto);
    }

    private StockAdjustmentDto toDto(StockAdjustment adj) {
        return new StockAdjustmentDto(
            adj.getId(),
            adj.getStockLevel().getId(),
            adj.getAdjustedBy().getId(),
            adj.getAdjDate(),
            adj.getAdjType(),
            adj.getQuantity()
        );
    }
}
