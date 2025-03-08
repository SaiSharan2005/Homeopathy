package com.G19.hospital.controller.prescription;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;  // Ensure this file exists in com.G19.hospital.dto
import com.G19.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto created = prescriptionService.createPrescription(prescriptionDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable Long id) {
        PrescriptionDto prescriptionDto = prescriptionService.getPrescriptionById(id);
        return new ResponseEntity<>(prescriptionDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        List<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> updatePrescription(@PathVariable Long id, @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto updated = prescriptionService.updatePrescription(id, prescriptionDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // New endpoint to add a prescription item to an existing prescription
    @PostMapping("/{id}/items")
    public ResponseEntity<PrescriptionDto> addPrescriptionItem(
            @PathVariable Long id,
            @RequestBody PrescriptionItemDto prescriptionItemDto) {
        PrescriptionDto updatedPrescription = prescriptionService.addPrescriptionItem(id, prescriptionItemDto);
        return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
    }
}
