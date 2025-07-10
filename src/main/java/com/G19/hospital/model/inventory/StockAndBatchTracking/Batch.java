package com.G19.hospital.model.inventory.StockAndBatchTracking;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.InventoryItem;

/**
 * Represents a production batch or lot for an inventory item.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batches",
       indexes = {
         @Index(name = "idx_batch_drug", columnList = "inventory_item_id"),
         @Index(name = "idx_batch_expiry", columnList = "expiry_date")
       }
)
public class Batch extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @Column(name = "batch_number", nullable = false, length = 100)
    private String batchNumber;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BatchStatus status;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<StockLevel> stockLevels = new HashSet<>();

    public enum BatchStatus {
        ACTIVE,
        QUARANTINED,
        EXPIRED
    }
}

/**
 * Tracks the current stock level of a batch at a specific location.
 */