package com.G19.hospital.controller;

import com.G19.hospital.DTO.PrescriptionDto;
import com.G19.hospital.model.Prescription;
import com.G19.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Endpoint for a doctor to upload a prescription with an image
    @PostMapping
    public ResponseEntity<Prescription> createPrescription(
            @RequestPart("prescriptionDto") PrescriptionDto prescriptionDto,
            @RequestPart("file") MultipartFile file) {
        Prescription createdPrescription = prescriptionService.createPrescription(prescriptionDto, file);
        return ResponseEntity.ok(createdPrescription);
    }

    // Get prescription by id
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(prescription);
    }

    // Get all prescriptions
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }

    // Patient can see his/her prescriptions
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return ResponseEntity.ok(prescriptions);
    }

    // Doctor can see his/her prescriptions
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return ResponseEntity.ok(prescriptions);
    }

    // Update a prescription (without file update; to update image, a similar method accepting MultipartFile could be added)
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id,
                                                           @RequestBody PrescriptionDto prescriptionDto) {
        Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDto);
        return ResponseEntity.ok(updatedPrescription);
    }

    // Delete a prescription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
