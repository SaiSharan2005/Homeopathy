// src/main/java/com/G19/hospital/DTO/inventory/StockAndBatchTracking/StockLevelDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockLevelDto {
    private Long id;
    private Long batchId;
    private Long warehouseId;
    private int quantityOnHand;
    private int reservedQuantity;
}
