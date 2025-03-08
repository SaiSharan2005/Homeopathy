package com.G19.hospital.service;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import java.util.List;

public interface PrescriptionService {
    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);
    PrescriptionDto updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto);
    void deletePrescription(Long prescriptionId);
    PrescriptionDto getPrescriptionById(Long prescriptionId);
    List<PrescriptionDto> getAllPrescriptions();
    PrescriptionDto addPrescriptionItem(Long prescriptionId, PrescriptionItemDto prescriptionItemDto);

}
