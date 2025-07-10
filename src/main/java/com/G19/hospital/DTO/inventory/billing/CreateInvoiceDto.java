// src/main/java/com/G19/hospital/DTO/billing/CreateInvoiceDto.java
package com.G19.hospital.DTO.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateInvoiceDto {
    private Long patientId;
    private LocalDate issueDate;      // optional, defaults to today
    private LocalDate dueDate;
    private InvoiceStatus status;     // optional, defaults to PENDING
    private List<CreateInvoiceItemDto> items;
}
