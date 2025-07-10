package com.G19.hospital.DTO.inventory.PurchaseAndReceipt;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGoodsReceiptItemDTO {
    private Long inventoryItemId;
    private String batchNumber;
    private LocalDate expiryDate;
    private int quantityReceived;
}