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

    // Quantity of the item available in this warehouse
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // The InventoryItem that this record is associated with
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    // The Warehouse where this record is stored
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
