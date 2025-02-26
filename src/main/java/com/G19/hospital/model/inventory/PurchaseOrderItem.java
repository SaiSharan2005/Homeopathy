package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItem extends AuditableBaseEntity {

    @Column(name = "order_item_id", unique = true)
    private Long orderItemId;
    
    @Column(name = "quantity_ordered")
    private int quantityOrdered;
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    
    // Many PurchaseOrderItems belong to one PurchaseOrder
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;
    
    // The InventoryItem that is being ordered
    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;
    
    // Calculate the total price for this line item
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(new BigDecimal(quantityOrdered));
    }
}
