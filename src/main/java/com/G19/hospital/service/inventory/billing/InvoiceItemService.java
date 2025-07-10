// InvoiceItemService.java
package com.G19.hospital.service.inventory.billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceItemDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceItemDto;

import java.util.List;

public interface InvoiceItemService {
    InvoiceItemDto create(CreateInvoiceItemDto dto);
    InvoiceItemDto update(Long itemId, CreateInvoiceItemDto dto);
    void delete(Long itemId);
    InvoiceItemDto getById(Long itemId);
    Page<InvoiceItemDto> getByInvoice(Long invoiceId, Pageable pageable);
    List<InvoiceItemDto> getByInvoice(Long invoiceId);
}
