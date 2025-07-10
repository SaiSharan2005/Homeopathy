// src/main/java/com/G19/hospital/DTO/billing/InvoiceDto.java
package com.G19.hospital.DTO.inventory.billing;

import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data 
@AllArgsConstructor
public class InvoiceDto {
    private Long invoiceId;
    private Long patientId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal amountDue;
    private InvoiceStatus status;
    private List<InvoiceItemDto> items;
}
