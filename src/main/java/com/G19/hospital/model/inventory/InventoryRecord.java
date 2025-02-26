package com.G19.hospital.model.inventory;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_records")
public class InventoryRecord extends AuditableBaseEntity {

    // Quantity of the item in this warehouse
    @Column(name = "quantity")
    private int quantity;
    
    // The InventoryItem that this record is for
    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;
    
    // The Warehouse where this record is stored
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
    // Getter for quantity (if you wish to add extra logic, otherwise Lombok generates it)
    public int getQuantity() {
        return this.quantity;
    }
}
