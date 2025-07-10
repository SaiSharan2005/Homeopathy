package com.G19.hospital.model.inventory.StockAndBatchTracking;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.Warehouse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_levels",
       indexes = {
         @Index(name = "idx_stock_batch", columnList = "batch_id"),
         @Index(name = "idx_stock_loc", columnList = "warehouse_id")
       }
)
public class StockLevel extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity_on_hand", nullable = false)
    private int quantityOnHand;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    @OneToMany(mappedBy = "stockLevel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<StockAdjustment> adjustments = new HashSet<>();
}
