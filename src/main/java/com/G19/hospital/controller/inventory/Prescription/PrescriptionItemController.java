package com.G19.hospital.controller.inventory.Prescription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionItemDto;
import com.G19.hospital.DTO.inventory.Prescription.PrescriptionItemDto;
import com.G19.hospital.service.inventory.Prescription.PrescriptionItemService;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions/items")
@RequiredArgsConstructor
public class PrescriptionItemController {

    private final PrescriptionItemService itemService;

    /** 1. Create a prescription item */
    @PostMapping
    public ResponseEntity<PrescriptionItemDto> create(
            @Valid @RequestBody CreatePrescriptionItemDto dto) 
    {
        PrescriptionItemDto created = itemService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 2. Get an item by ID */
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    /** 3. Get all items for a prescription */
    @GetMapping("/prescription/{prescriptionId}")
    public ResponseEntity<List<PrescriptionItemDto>> getByPrescription(
            @PathVariable Long prescriptionId) 
    {
        return ResponseEntity.ok(itemService.getAllByPrescription(prescriptionId));
    }

    /** 4. Update an item */
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionItemDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CreatePrescriptionItemDto dto) 
    {
        return ResponseEntity.ok(itemService.update(id, dto));
    }

    /** 5. Delete an item */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
