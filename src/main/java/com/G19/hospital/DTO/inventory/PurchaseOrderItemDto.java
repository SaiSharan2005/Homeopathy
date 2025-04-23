package com.G19.hospital.DTO.inventory;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemDto {
    // Optional business identifier; if auto-generated, you may omit it in create requests.
    private Long orderItemId;
    private int quantityOrdered;
    private BigDecimal unitPrice;
    // References to the parent order and inventory item
    private Long purchaseOrderId;
    private Long inventoryItemId;
}
