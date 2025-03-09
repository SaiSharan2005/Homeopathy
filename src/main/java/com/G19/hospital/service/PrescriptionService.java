package com.G19.hospital.service;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(Prescription prescription);
    Prescription updatePrescription(Long prescriptionId, Prescription prescription);
    void deletePrescription(Long prescriptionId);
    Prescription getPrescriptionById(Long prescriptionId);
    List<Prescription> getAllPrescriptions();
    Prescription addPrescriptionItem(Long prescriptionId, PrescriptionItem prescriptionItem);

    // New methods for finding prescriptions
    Prescription getPrescriptionByBookingId(Long bookingId);
    Prescription getPrescriptionByToken(String token);
    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
    List<Prescription> getPrescriptionsByPatient(Long patientId);
}

