package com.G19.hospital.service;

import com.G19.hospital.DTO.PrescriptionDto;
import com.G19.hospital.model.Prescription;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(PrescriptionDto prescriptionDto, MultipartFile file);
    Prescription getPrescriptionById(Long id);
    List<Prescription> getAllPrescriptions();
    List<Prescription> getPrescriptionsByPatient(Long patientId);
    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
    Prescription updatePrescription(Long id, PrescriptionDto prescriptionDto);
    void deletePrescription(Long id);
}
