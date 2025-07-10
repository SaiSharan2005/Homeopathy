package com.G19.hospital.model.inventory.StockAndBatchTracking;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;

/**
 * Records any manual or automated adjustments to stock levels.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_adjustments",
       indexes = {@Index(name = "idx_adj_stock", columnList = "stock_id"),
                  @Index(name = "idx_adj_date", columnList = "adj_date")}
)
public class StockAdjustment extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockLevel stockLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adjusted_by", nullable = false)
    private User adjustedBy;

    @Column(name = "adj_date", nullable = false)
    private LocalDateTime adjDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "adj_type", nullable = false)
    private AdjustmentType adjType;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public enum AdjustmentType {
        DAMAGE,
        COUNT,
        RECALL,
        OTHER
    }
}
