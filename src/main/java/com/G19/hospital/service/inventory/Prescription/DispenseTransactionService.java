// src/main/java/com/G19/hospital/service/prescription/DispenseTransactionService.java
package com.G19.hospital.service.inventory.Prescription;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.Prescription.CreateDispenseDto;
import com.G19.hospital.DTO.inventory.Prescription.DispenseDto;

import java.util.List;

public interface DispenseTransactionService {
    DispenseDto create(CreateDispenseDto dto);
    DispenseDto update(Long id, CreateDispenseDto dto);
    void delete(Long id);
    DispenseDto getById(Long id);
    Page<DispenseDto> getAll(Pageable pageable);
    /** Batch‑create dispense transactions for every PrescriptionItem in a Prescription */
    List<DispenseDto> createFromPrescription(Long prescriptionId,
                                              Long dispensedById,
                                              Long warehouseId);
}
