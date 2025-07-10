package com.G19.hospital.model.inventory.prescription;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.StockAndBatchTracking.Batch;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.Warehouse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dispense_transactions", indexes = {
    @Index(name = "idx_disp_rx_item", columnList = "rx_item_id"),
    @Index(name = "idx_disp_date", columnList = "dispense_date"),
    @Index(name = "idx_disp_loc", columnList = "location_id")
})
public class DispenseTransaction extends AuditableBaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "rx_item_id", nullable = false)
  private PrescriptionItem rxItem;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "dispensed_by", nullable = false)
  private User dispensedBy;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "batch_id", nullable = false)
  private Batch batch;

  @Column(name = "dispense_date", nullable = false)
  private LocalDateTime dispenseDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "location_id", nullable = false)
  private Warehouse warehouse;
}
