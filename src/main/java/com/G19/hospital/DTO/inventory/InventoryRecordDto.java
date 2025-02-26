package com.G19.hospital.DTO.inventory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRecordDto {
    private Long id;
    private int quantity;
    // References to related entities by their IDs
    private Long inventoryItemId;
    private Long warehouseId;
}
