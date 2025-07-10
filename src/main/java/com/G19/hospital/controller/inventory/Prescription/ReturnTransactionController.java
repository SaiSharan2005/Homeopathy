package com.G19.hospital.controller.inventory.Prescription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.Prescription.CreateReturnDto;
import com.G19.hospital.DTO.inventory.Prescription.ReturnDto;
import com.G19.hospital.service.inventory.Prescription.ReturnTransactionService;

@RestController
@RequestMapping("/api/return-transactions")
@RequiredArgsConstructor
public class ReturnTransactionController {

    private final ReturnTransactionService returnService;

    /** 1. Create a new ReturnTransaction */
    @PostMapping
    public ResponseEntity<ReturnDto> createReturn(
            @Valid @RequestBody CreateReturnDto dto) 
    {
        ReturnDto created = returnService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** 2. Get a ReturnTransaction by ID */
    @GetMapping("/{id}")
    public ResponseEntity<ReturnDto> getById(@PathVariable Long id) {
        ReturnDto dto = returnService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /** 3. Get all ReturnTransactions (paginated) */
    @GetMapping
    public ResponseEntity<Page<ReturnDto>> getAll(Pageable pageable) {
        Page<ReturnDto> page = returnService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    /** 4. Update an existing ReturnTransaction */
    @PutMapping("/{id}")
    public ResponseEntity<ReturnDto> updateReturn(
            @PathVariable Long id,
            @Valid @RequestBody CreateReturnDto dto) 
    {
        ReturnDto updated = returnService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** 5. Delete a ReturnTransaction */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReturn(@PathVariable Long id) {
        returnService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
