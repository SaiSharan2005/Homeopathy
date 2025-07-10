// src/main/java/com/G19/hospital/DTO/billing/InvoiceItemDto.java
package com.G19.hospital.DTO.inventory.billing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data @AllArgsConstructor
public class InvoiceItemDto {
    private Long itemId;
    private Long invoiceId;
    private Long dispenseTransactionId;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
