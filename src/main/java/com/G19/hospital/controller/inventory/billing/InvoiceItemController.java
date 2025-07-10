package com.G19.hospital.controller.inventory.billing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceItemDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceItemDto;
import com.G19.hospital.service.inventory.billing.InvoiceItemService;

import java.util.List;

@RestController
@RequestMapping("/api/invoices/{invoiceId}/items")
@RequiredArgsConstructor
public class InvoiceItemController {

    private final InvoiceItemService itemService;

    /** 9a. Create an invoice item */
    @PostMapping
    public ResponseEntity<InvoiceItemDto> createItem(
            @PathVariable("invoiceId") Long invoiceId,
            @Valid @RequestBody CreateInvoiceItemDto dto) 
    {
        dto.setInvoiceId(invoiceId);
        InvoiceItemDto created = itemService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 9b. Update an invoice item */
    @PutMapping("/{itemId}")
    public ResponseEntity<InvoiceItemDto> updateItem(
            @PathVariable("invoiceId") Long invoiceId,
            @PathVariable("itemId") Long itemId,
            @Valid @RequestBody CreateInvoiceItemDto dto) 
    {
        dto.setInvoiceId(invoiceId);
        InvoiceItemDto updated = itemService.update(itemId, dto);
        return ResponseEntity.ok(updated);
    }

    /** 9c. Delete an invoice item */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable("invoiceId") Long invoiceId,
            @PathVariable("itemId") Long itemId) 
    {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }

    /** 10a. Get a single invoice item by ID */
    @GetMapping("/{itemId}")
    public ResponseEntity<InvoiceItemDto> getItemById(
            @PathVariable("invoiceId") Long invoiceId,
            @PathVariable("itemId") Long itemId) 
    {
        InvoiceItemDto dto = itemService.getById(itemId);
        return ResponseEntity.ok(dto);
    }

    /** 10b. Get all items for an invoice (paginated) */
    @GetMapping
    public ResponseEntity<Page<InvoiceItemDto>> getItemsPageable(
            @PathVariable("invoiceId") Long invoiceId,
            Pageable pageable) 
    {
        return ResponseEntity.ok(itemService.getByInvoice(invoiceId, pageable));
    }

    /** 10c. Get all items for an invoice (non‑paginated) */
    @GetMapping("/all")
    public ResponseEntity<List<InvoiceItemDto>> getItemsAll(
            @PathVariable("invoiceId") Long invoiceId) 
    {
        return ResponseEntity.ok(itemService.getByInvoice(invoiceId));
    }
}
