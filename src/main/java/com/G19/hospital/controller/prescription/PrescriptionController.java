package com.G19.hospital.controller.prescription;

import com.G19.hospital.DTO.prescription.InstructionDto;
import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import com.G19.hospital.model.prescription.Prescription;
import com.G19.hospital.service.PrescriptionService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    // @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionDto prescriptionDto) {
        Prescription created = prescriptionService.createPrescription(prescriptionDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id,
            @RequestBody PrescriptionDto prescriptionDto) {
        Prescription updated = prescriptionService.updatePrescription(id, prescriptionDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint to add a prescription item to an existing prescription
    @PostMapping("/{id}/items")
    public ResponseEntity<Prescription> addPrescriptionItem(@PathVariable Long id,
            @RequestBody PrescriptionItemDto prescriptionItemDto) {
        Prescription updatedPrescription = prescriptionService.addPrescriptionItem(id, prescriptionItemDto);
        return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
    }

    // New endpoint: find prescription by Booking Appointment ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Prescription> getPrescriptionByBookingId(@PathVariable Long bookingId) {
        Prescription prescription = prescriptionService.getPrescriptionByBookingId(bookingId);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    // New endpoint: find prescription by Booking Appointment Token
    @GetMapping("/token/{token}")
    public ResponseEntity<Prescription> getPrescriptionByToken(@PathVariable String token) {
        Prescription prescription = prescriptionService.getPrescriptionByToken(token);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    // New endpoint: find prescriptions by Doctor ID
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    // New endpoint: find prescriptions by Patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}/instructions")
    public ResponseEntity<List<InstructionDto>> getInstructionsByPatient(
            @PathVariable Long patientId) {
        List<InstructionDto> list = prescriptionService.getInstructionsByPatient(patientId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
