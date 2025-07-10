package com.G19.hospital.controller.inventory.billing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.billing.CreatePaymentTermsDto;
import com.G19.hospital.DTO.inventory.billing.PaymentTermsDto;
import com.G19.hospital.service.inventory.billing.PaymentTermsService;

@RestController
@RequestMapping("/api/payment-terms")
@RequiredArgsConstructor
public class PaymentTermsController {

    private final PaymentTermsService paymentTermsService;

    /** Create a new PaymentTerms */
    @PostMapping
    public ResponseEntity<PaymentTermsDto> create(
            @Valid @RequestBody CreatePaymentTermsDto dto) 
    {
        PaymentTermsDto created = paymentTermsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Get a PaymentTerms by ID */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentTermsDto> getById(
            @PathVariable("id") Long id) 
    {
        PaymentTermsDto dto = paymentTermsService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /** Get all PaymentTerms (paginated) */
    @GetMapping
    public ResponseEntity<Page<PaymentTermsDto>> getAll(Pageable pageable) {
        Page<PaymentTermsDto> page = paymentTermsService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    /** Update an existing PaymentTerms */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentTermsDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreatePaymentTermsDto dto) 
    {
        PaymentTermsDto updated = paymentTermsService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** Delete a PaymentTerms */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        paymentTermsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
