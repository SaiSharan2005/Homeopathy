package com.G19.hospital.service.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionItemDto;
import com.G19.hospital.DTO.inventory.Prescription.*;

import java.util.List;

public interface PrescriptionItemService {
    PrescriptionItemDto create(CreatePrescriptionItemDto dto);
    PrescriptionItemDto update(Long id, CreatePrescriptionItemDto dto);
    void delete(Long id);
    PrescriptionItemDto getById(Long id);
    List<PrescriptionItemDto> getAllByPrescription(Long prescriptionId);
}
