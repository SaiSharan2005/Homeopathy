package com.G19.hospital.DTO.inventory;

import com.G19.hospital.model.inventory.PurchaseOrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {
    // Optional business identifier; if your system auto-generates it, you may omit it in the create request.
    private Long orderId;
    private LocalDateTime orderDate;
    private PurchaseOrderStatus status;
    private BigDecimal totalAmount;
    // Supplier reference (assumed to be an existing supplier's id)
    private Long supplierId;
    // Optionally, a list of order item DTOs
    private List<PurchaseOrderItemDto> purchaseOrderItems;
}
