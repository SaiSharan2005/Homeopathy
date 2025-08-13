package com.G19.hospital.DTO.inventory;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemDto {
    // Optional business identifier; if auto-generated, you may omit it in create requests.
    private Long orderItemId;
    @NotNull(message = "Quantity ordered is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantityOrdered;
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
    // References to the parent order and inventory item
    private Long purchaseOrderId;
    @NotNull(message = "Inventory item ID is required")
    private Long inventoryItemId;
}
