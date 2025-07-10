// PrescriptionItemServiceImpl.java
package com.G19.hospital.service.implement.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionItemDto;
import com.G19.hospital.DTO.inventory.Prescription.*;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.prescription.Prescription;
import com.G19.hospital.model.inventory.prescription.PrescriptionItem;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionItemRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.BatchRepository;
import com.G19.hospital.repository.inventory.core.InventoryItemRepository;
import com.G19.hospital.service.inventory.Prescription.PrescriptionItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
    private final PrescriptionItemRepository repo;
    private final PrescriptionRepository presRepo;
    private final InventoryItemRepository drugRepo;
    private final BatchRepository batchRepo;

    @Override
    public PrescriptionItemDto create(CreatePrescriptionItemDto dto) {
        Prescription p = presRepo.findById(dto.getPrescriptionId())
          .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        var drug = drugRepo.findById(dto.getDrugId())
          .orElseThrow(() -> new CustomSecurityException("Drug not found", HttpStatus.NOT_FOUND));
        var batch = batchRepo.findById(dto.getBatchId())
          .orElseThrow(() -> new CustomSecurityException("Batch not found", HttpStatus.NOT_FOUND));
        PrescriptionItem i = new PrescriptionItem();
        i.setPrescription(p);
        i.setDrug(drug);
        i.setFrequency(dto.getFrequency());
        i.setDuration(dto.getDuration());
        i.setQuantity(dto.getQuantity());
        i.setAdditionalInstructions(dto.getAdditionalInstructions());
        repo.save(i);
        return toDto(i);
    }

    @Override
    public PrescriptionItemDto update(Long id, CreatePrescriptionItemDto dto) {
        PrescriptionItem i = repo.findById(id)
          .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
        // fields update...
        i.setFrequency(dto.getFrequency());
        i.setDuration(dto.getDuration());
        i.setQuantity(dto.getQuantity());
        i.setAdditionalInstructions(dto.getAdditionalInstructions());
        repo.save(i);
        return toDto(i);
    }

    @Override
    public void delete(Long id) {
        if(!repo.existsById(id))
          throw new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public PrescriptionItemDto getById(Long id) {
        return repo.findById(id).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public List<PrescriptionItemDto> getAllByPrescription(Long presId) {
        presRepo.findById(presId).orElseThrow(() ->
          new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        return repo.findAll().stream()
          .filter(i -> i.getPrescription().getId().equals(presId))
          .map(this::toDto)
          .collect(Collectors.toList());
    }

    private PrescriptionItemDto toDto(PrescriptionItem i) {
        return new PrescriptionItemDto(
          i.getId(),
          i.getPrescription().getId(),
          i.getDrug().getId(),
          i.getFrequency(),
          i.getDuration(),
          i.getQuantity(),
          i.getAdditionalInstructions());
    }
}
