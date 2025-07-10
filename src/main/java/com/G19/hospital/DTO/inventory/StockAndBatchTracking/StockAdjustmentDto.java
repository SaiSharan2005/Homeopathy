// src/main/java/com/G19/hospital/DTO/inventory/StockAndBatchTracking/StockAdjustmentDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import com.G19.hospital.model.inventory.StockAndBatchTracking.StockAdjustment.AdjustmentType;

@Data
@AllArgsConstructor
public class StockAdjustmentDto {
    private Long id;
    private Long stockLevelId;
    private Long adjustedById;
    private LocalDateTime adjDate;
    private AdjustmentType adjType;
    private int quantity;
}
