// src/main/java/com/G19/hospital/DTO/inventory/StockAndBatchTracking/CreateStockAdjustmentDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import com.G19.hospital.model.inventory.StockAndBatchTracking.StockAdjustment.AdjustmentType;

@Data
public class CreateStockAdjustmentDto {
    @NotNull
    private Long stockLevelId;
    @NotNull
    private Long adjustedById;
    // If null, service will default to now()
    private LocalDateTime adjDate;
    @NotNull
    private AdjustmentType adjType;
    @Min(1)
    private int quantity;
}
