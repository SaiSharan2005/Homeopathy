// src/main/java/com/G19/hospital/DTO/inventory/StockAndBatchTracking/CreateStockLevelDto.java
package com.G19.hospital.DTO.inventory.StockAndBatchTracking;

import lombok.Data;

@Data
public class CreateStockLevelDto {
    private Long batchId;
    private Long warehouseId;
    private int quantityOnHand;
    private int reservedQuantity;
}
