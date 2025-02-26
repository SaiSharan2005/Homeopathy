package com.G19.hospital.DTO.inventory;

import com.G19.hospital.model.inventory.InventoryTransactionType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransactionDto {
    private Long id;
    private LocalDateTime transactionDate;
    private int quantityChange;
    private InventoryTransactionType transactionType;
    private String reference;
    private String comments;
    // Reference to the InventoryItem by its id
    private Long inventoryItemId;
}
