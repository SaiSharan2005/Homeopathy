package com.G19.hospital.DTO.inventory.PurchaseAndReceipt;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReceiptDTO {
    private Long id;
    private Long purchaseOrderId;
    private LocalDateTime receiptDate;
    private Long receivedById;
    private String remarks;
    private List<GoodsReceiptItemDTO> items;
}
