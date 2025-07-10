package com.G19.hospital.controller.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionDto;
import com.G19.hospital.DTO.inventory.Prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.InstructionDto;
import com.G19.hospital.service.inventory.Prescription.PrescriptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    /** 1. Create a prescription */
    @PostMapping
    public ResponseEntity<PrescriptionDto> create(
            @Valid @RequestBody CreatePrescriptionDto dto) 
    {
        PrescriptionDto created = prescriptionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 2. Get by ID */
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getById(id));
    }

    /** 3. Get all (paginated) */
    @GetMapping
    public ResponseEntity<Page<PrescriptionDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(prescriptionService.getAll(pageable));
    }

    /** 4. Update */
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CreatePrescriptionDto dto) 
    {
        return ResponseEntity.ok(prescriptionService.update(id, dto));
    }

    /** 5. Delete */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prescriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** 6. Get by booking ID */
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PrescriptionDto> byBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(prescriptionService.getByBookingId(bookingId));
    }

    /** 7. Get by token */
    @GetMapping("/token/{token}")
    public ResponseEntity<PrescriptionDto> byToken(@PathVariable String token) {
        return ResponseEntity.ok(prescriptionService.getByToken(token));
    }

    /** 8. Get by doctor ID (paginated) */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Page<PrescriptionDto>> byDoctor(
            @PathVariable Long doctorId,
            Pageable pageable) 
    {
        return ResponseEntity.ok(prescriptionService.getByDoctor(doctorId, pageable));
    }

    /** 9. Get by patient ID (paginated) */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<PrescriptionDto>> byPatient(
            @PathVariable Long patientId,
            Pageable pageable) 
    {
        return ResponseEntity.ok(prescriptionService.getByPatient(patientId, pageable));
    }

    /** 10. Get instructions by patient */
    @GetMapping("/patient/{patientId}/instructions")
    public ResponseEntity<List<InstructionDto>> instructions(
            @PathVariable Long patientId) 
    {
        return ResponseEntity.ok(prescriptionService.getInstructionsByPatient(patientId));
    }
}
