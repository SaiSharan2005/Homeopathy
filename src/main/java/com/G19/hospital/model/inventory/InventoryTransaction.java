package com.G19.hospital.model.inventory;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.InventoryItem;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transactions")
public class InventoryTransaction extends AuditableBaseEntity {

    // The date and time when the transaction occurred
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    // The quantity change in this transaction
    @Column(name = "quantity_change", nullable = false)
    private int quantityChange;
    
    // Indicates the type of transaction (e.g., PURCHASE, CONSUMPTION, ADJUSTMENT, RETURN)
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private InventoryTransactionType transactionType;
    
    // Optional reference for additional context
    @Column(name = "reference", length = 500)
    private String reference;
    
    // Optional comments about the transaction
    @Column(name = "comments", length = 1000)
    private String comments;
    
    // The InventoryItem associated with this transaction
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;
    
    // The user who performed the transaction (for audit purposes)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User performedBy;
}
