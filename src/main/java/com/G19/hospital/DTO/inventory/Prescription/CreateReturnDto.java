// src/main/java/com/G19/hospital/DTO/prescription/CreateReturnDto.java
package com.G19.hospital.DTO.inventory.Prescription;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReturnDto {
    @NotNull
    private Long dispenseTransactionId;
    @NotNull
    private Long returnedById;
    private LocalDateTime returnDate; // optional, defaults to now
    @Min(1)
    private int quantityReturned;
    private String reason;
}
