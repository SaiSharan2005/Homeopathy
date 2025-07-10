// PrescriptionService.java
package com.G19.hospital.service.inventory.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.G19.hospital.DTO.prescription.InstructionDto;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionDto;
import com.G19.hospital.DTO.inventory.Prescription.PrescriptionDto;

import java.util.List;

public interface PrescriptionService {
    PrescriptionDto create(CreatePrescriptionDto dto);
    PrescriptionDto update(Long id, CreatePrescriptionDto dto);
    void delete(Long id);
    PrescriptionDto getById(Long id);
    Page<PrescriptionDto> getAll(Pageable pageable);
    PrescriptionDto getByBookingId(Long bookingId);
    PrescriptionDto getByToken(String token);
    Page<PrescriptionDto> getByDoctor(Long doctorId, Pageable pageable);
    Page<PrescriptionDto> getByPatient(Long patientId, Pageable pageable);
    List<InstructionDto> getInstructionsByPatient(Long patientId);
}