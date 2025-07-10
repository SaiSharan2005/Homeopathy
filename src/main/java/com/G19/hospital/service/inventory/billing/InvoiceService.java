// InvoiceService.java
package com.G19.hospital.service.inventory.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceDto;
import com.G19.hospital.DTO.inventory.billing.RecordPaymentDto;

public interface InvoiceService {
    InvoiceDto create(CreateInvoiceDto dto);
    InvoiceDto update(Long invoiceId, CreateInvoiceDto dto);
    void delete(Long invoiceId);
    InvoiceDto getById(Long invoiceId);
    Page<InvoiceDto> getAll(Pageable pageable);
    Page<InvoiceDto> getByPatient(Long patientId, Pageable pageable);
    Page<InvoiceDto> getByStatus(String status, Pageable pageable);
    InvoiceDto recordPayment(RecordPaymentDto dto);
}
