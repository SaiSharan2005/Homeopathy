package com.G19.hospital.model.inventory;

import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.InventoryItem;
import com.G19.hospital.model.inventory.core.Warehouse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_records")
public class InventoryRecord extends AuditableBaseEntity {

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    @JsonBackReference("inventoryItem-records")
    private InventoryItem inventoryItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
