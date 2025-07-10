package com.G19.hospital.controller.inventory.billing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceDto;
import com.G19.hospital.DTO.inventory.billing.RecordPaymentDto;
import com.G19.hospital.service.inventory.billing.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    /** 1. Create an invoice */
    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody CreateInvoiceDto dto) {
        InvoiceDto created = invoiceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 2. Update an invoice */
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateInvoiceDto dto) 
    {
        InvoiceDto updated = invoiceService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /** 3. Delete an invoice */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** 4. Get invoice by ID */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable("id") Long id) {
        InvoiceDto dto = invoiceService.getById(id);
        return ResponseEntity.ok(dto);
    }

    /** 5. Get all invoices (paginated) */
    @GetMapping
    public ResponseEntity<Page<InvoiceDto>> getAllInvoices(Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getAll(pageable));
    }

    /** 6. Record a payment on an invoice */
    @PostMapping("/{id}/payments")
    public ResponseEntity<InvoiceDto> recordPayment(
            @PathVariable("id") Long id,
            @Valid @RequestBody RecordPaymentDto dto) 
    {
        // enforce path‐param and DTO id match
        dto.setInvoiceId(id);
        InvoiceDto updated = invoiceService.recordPayment(dto);
        return ResponseEntity.ok(updated);
    }

    /** 7. Get all invoices for a patient (paginated) */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<InvoiceDto>> getByPatient(
            @PathVariable("patientId") Long patientId,
            Pageable pageable) 
    {
        return ResponseEntity.ok(invoiceService.getByPatient(patientId, pageable));
    }

    /** 8. Get all invoices by status (paginated) */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<InvoiceDto>> getByStatus(
            @PathVariable("status") String status,
            Pageable pageable) 
    {
        return ResponseEntity.ok(invoiceService.getByStatus(status, pageable));
    }
}
