

// src/main/java/com/G19/hospital/DTO/inventory/PurchaseOrderDto.java
package com.G19.hospital.DTO.inventory;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.G19.hospital.model.inventory.PurchaseAndReceipt.PurchaseOrderStatus;

@Data
public class PurchaseOrderDto {
    private Long orderId;                       // null on create
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;                    // required
    private LocalDateTime orderDate;            // optional (defaults to now)
    private PurchaseOrderStatus status;         // optional (defaults to CREATED)
    private BigDecimal totalAmount;             // computed by server
    private List<PurchaseOrderItemDto> items;   // items are now optional
}
