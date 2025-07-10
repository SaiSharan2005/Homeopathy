// src/main/java/com/G19/hospital/DTO/billing/CreatePaymentTermsDto.java
package com.G19.hospital.DTO.inventory.billing;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePaymentTermsDto {
    @NotBlank
    private String name;           // e.g. Net30, DueOnReceipt
    @Min(0)
    private Integer daysUntilDue;  // e.g. 30
}
