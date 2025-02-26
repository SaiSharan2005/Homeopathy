package com.G19.hospital.model.inventory;

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

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    
    @Column(name = "quantity_change")
    private int quantityChange;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private InventoryTransactionType transactionType;
    
    @Column(name = "reference", length = 500)
    private String reference;
    
    @Column(name = "comments", length = 1000)
    private String comments;
    
    // The InventoryItem associated with this transaction
    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;
}
