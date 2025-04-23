package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder extends AuditableBaseEntity {

    @Column(name = "order_id", unique = true)
    private Long orderId;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseOrderStatus status;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    // The supplier from whom the order is placed
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    // One PurchaseOrder includes many PurchaseOrderItems
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private Set<PurchaseOrderItem> purchaseOrderItems = new HashSet<>();
    
    // Calculate the total amount based on the order items
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseOrderItem item : purchaseOrderItems) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }
}

