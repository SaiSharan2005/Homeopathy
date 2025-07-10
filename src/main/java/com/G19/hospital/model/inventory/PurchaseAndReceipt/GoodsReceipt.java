package com.G19.hospital.model.inventory.PurchaseAndReceipt;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;

/**
 * Represents a goods receipt against a purchase order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_receipts",
       indexes = {
         @Index(name = "idx_gr_po", columnList = "purchase_order_id"),
         @Index(name = "idx_gr_date", columnList = "receipt_date")
       }
)
public class GoodsReceipt extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(name = "receipt_date", nullable = false)
    private LocalDateTime receiptDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "received_by", nullable = false)
    private User receivedBy;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @OneToMany(mappedBy = "goodsReceipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<GoodsReceiptItem> items = new HashSet<>();

    /**
     * Convenience method to add an item to this receipt.
     */
    public void addItem(GoodsReceiptItem item) {
        item.setGoodsReceipt(this);
        this.items.add(item);
    }
}


/**
 * Represents a single line item within a goods receipt.
 */
