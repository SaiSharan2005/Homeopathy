package com.G19.hospital.controller.prescription;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
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
    public ResponseEntity<PrescriptionDto> updatePrescription(@PathVariable Long id,
                                                                @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto updated = prescriptionService.updatePrescription(id, prescriptionDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint to add a prescription item to an existing prescription
    @PostMapping("/{id}/items")
    public ResponseEntity<PrescriptionDto> addPrescriptionItem(@PathVariable Long id,
                                                               @RequestBody PrescriptionItemDto prescriptionItemDto) {
        PrescriptionDto updatedPrescription = prescriptionService.addPrescriptionItem(id, prescriptionItemDto);
        return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
    }

    // New endpoint: find prescription by Booking Appointment ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PrescriptionDto> getPrescriptionByBookingId(@PathVariable Long bookingId) {
        PrescriptionDto dto = prescriptionService.getPrescriptionByBookingId(bookingId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // New endpoint: find prescription by Booking Appointment Token
    @GetMapping("/token/{token}")
    public ResponseEntity<PrescriptionDto> getPrescriptionByToken(@PathVariable String token) {
        PrescriptionDto dto = prescriptionService.getPrescriptionByToken(token);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // New endpoint: find prescriptions by Doctor ID
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<PrescriptionDto> dtos = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // New endpoint: find prescriptions by Patient ID (i.e., "mummy")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<PrescriptionDto> dtos = prescriptionService.getPrescriptionsByPatient(patientId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
