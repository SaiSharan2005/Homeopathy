package com.G19.hospital.model.inventory.PurchaseAndReceipt;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.InventoryItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_receipt_items",
       indexes = {
         @Index(name = "idx_gri_batch", columnList = "batch_number"),
         @Index(name = "idx_gri_expiry", columnList = "expiry_date")
       }
)
public class GoodsReceiptItem extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_receipt_id", nullable = false)
    private GoodsReceipt goodsReceipt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @Column(name = "batch_number", length = 100, nullable = false)
    private String batchNumber;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "quantity_received", nullable = false)
    private int quantityReceived;

    /**
     * Total value could be computed if unitCost is stored.
     */
}