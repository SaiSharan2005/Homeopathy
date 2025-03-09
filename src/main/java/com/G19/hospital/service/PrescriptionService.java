package com.G19.hospital.service;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import com.G19.hospital.model.prescription.Prescription;
import com.G19.hospital.model.prescription.PrescriptionItem;
import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(PrescriptionDto prescriptionDto);
    Prescription updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto);
    void deletePrescription(Long prescriptionId);
    Prescription getPrescriptionById(Long prescriptionId);
    List<Prescription> getAllPrescriptions();
    Prescription addPrescriptionItem(Long prescriptionId, PrescriptionItemDto prescriptionItemDto);
    
    Prescription getPrescriptionByBookingId(Long bookingId);
    Prescription getPrescriptionByToken(String token);
    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
    List<Prescription> getPrescriptionsByPatient(Long patientId);
}
