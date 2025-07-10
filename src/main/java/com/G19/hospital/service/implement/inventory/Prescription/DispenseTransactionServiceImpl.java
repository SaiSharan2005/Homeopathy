// src/main/java/com/G19/hospital/service/implement/prescription/DispenseTransactionServiceImpl.java
package com.G19.hospital.service.implement.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreateDispenseDto;
import com.G19.hospital.DTO.inventory.Prescription.DispenseDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch;
import com.G19.hospital.model.inventory.StockAndBatchTracking.StockLevel;
import com.G19.hospital.model.inventory.core.Warehouse;
import com.G19.hospital.model.inventory.prescription.DispenseTransaction;
import com.G19.hospital.model.inventory.prescription.Prescription;
import com.G19.hospital.model.inventory.prescription.PrescriptionItem;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.Prescription.DispenseTransactionRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionItemRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.BatchRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.StockLevelRepository;
import com.G19.hospital.repository.inventory.core.WarehouseRepository;
import com.G19.hospital.service.inventory.Prescription.DispenseTransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DispenseTransactionServiceImpl implements DispenseTransactionService {

    private final DispenseTransactionRepository repo;
    private final PrescriptionItemRepository itemRepo;
    private final PrescriptionRepository presRepo;
    private final UserRepository userRepo;
    private final BatchRepository batchRepo;
    private final WarehouseRepository warehouseRepo;
    private final StockLevelRepository stockLevelRepo;


    @Override
    public DispenseDto create(CreateDispenseDto dto) {
        // Lookup related entities
        PrescriptionItem rxItem = itemRepo.findById(dto.getRxItemId())
            .orElseThrow(() -> new CustomSecurityException("PrescriptionItem not found", HttpStatus.NOT_FOUND));
        User user = userRepo.findById(dto.getDispensedById())
            .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));
        Batch batch = batchRepo.findById(dto.getBatchId())
            .orElseThrow(() -> new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND));
        Warehouse wh = warehouseRepo.findById(dto.getWarehouseId())
            .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));

        DispenseTransaction d = new DispenseTransaction();
        d.setRxItem(rxItem);
        d.setDispensedBy(user);
        d.setBatch(batch);
        d.setWarehouse(wh);
        d.setDispenseDate(dto.getDispenseDate() != null ? dto.getDispenseDate() : LocalDateTime.now());

        DispenseTransaction saved = repo.save(d);
        return toDto(saved);
    }

    @Override
    public DispenseDto update(Long id, CreateDispenseDto dto) {
        DispenseTransaction d = repo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("DispenseTransaction not found", HttpStatus.NOT_FOUND));
        if (dto.getDispenseDate() != null) d.setDispenseDate(dto.getDispenseDate());
        // You can similarly update rxItem, batch, etc., if allowed
        repo.save(d);
        return toDto(d);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new CustomSecurityException("DispenseTransaction not found", HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DispenseDto getById(Long id) {
        return repo.findById(id).map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException("DispenseTransaction not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispenseDto> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }


    @Override
    public List<DispenseDto> createFromPrescription(Long prescriptionId,
                                                     Long dispensedById,
                                                     Long warehouseId) {
        Prescription pres = presRepo.findById(prescriptionId)
            .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        User user = userRepo.findById(dispensedById)
            .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));
        Warehouse wh = warehouseRepo.findById(warehouseId)
            .orElseThrow(() -> new CustomSecurityException("Warehouse not found", HttpStatus.NOT_FOUND));

        return pres.getItems().stream().map(item -> {
            // For each prescribed drug, pick the latest batch in this warehouse
            Long invItemId = item.getDrug().getId();
            StockLevel latest = stockLevelRepo
                .findFirstByBatch_InventoryItem_IdAndWarehouse_IdOrderByBatch_ExpiryDateDesc(invItemId, warehouseId)
                .orElseThrow(() -> new CustomSecurityException(
                    "No stock for item " + invItemId + " in warehouse " + warehouseId,
                    HttpStatus.BAD_REQUEST));

            DispenseTransaction d = new DispenseTransaction();
            d.setRxItem(item);
            d.setDispensedBy(user);
            d.setBatch(latest.getBatch());
            d.setWarehouse(wh);
            d.setDispenseDate(LocalDateTime.now());
            return repo.save(d);
        })
        .map(this::toDto)
        .collect(Collectors.toList());
    }
    private DispenseDto toDto(DispenseTransaction d) {
        return new DispenseDto(
            d.getId(),
            d.getRxItem().getId(),
            d.getDispensedBy().getId(),
            d.getBatch().getId(),
            d.getDispenseDate(),
            d.getWarehouse().getId()
        );
    }
}
