// src/main/java/com/G19/hospital/DTO/prescription/CreateDispenseDto.java
package com.G19.hospital.DTO.inventory.Prescription;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDispenseDto {
    @NotNull private Long rxItemId;
    @NotNull private Long dispensedById;
    @NotNull private Long batchId;
    @NotNull private Long warehouseId;
    // optional, defaults to now()
    private LocalDateTime dispenseDate;
}
