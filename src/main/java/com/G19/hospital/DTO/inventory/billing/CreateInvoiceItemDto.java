// src/main/java/com/G19/hospital/DTO/billing/CreateInvoiceItemDto.java
package com.G19.hospital.DTO.inventory.billing;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateInvoiceItemDto {
    private Long invoiceId;
    private Long dispenseTransactionId;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
}
