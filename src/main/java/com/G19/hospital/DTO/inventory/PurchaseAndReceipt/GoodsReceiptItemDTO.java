package com.G19.hospital.DTO.inventory.PurchaseAndReceipt;

import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiptItemDTO {
    private Long id;
    private Long inventoryItemId;
    private String batchNumber;
    private LocalDate expiryDate;
    private int quantityReceived;
}
