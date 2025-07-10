package com.G19.hospital.model.inventory.prescription;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;
import com.G19.hospital.model.inventory.core.Warehouse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "return_transactions",
       indexes = {
         @Index(name = "idx_return_disp", columnList = "dispense_id"),
         @Index(name = "idx_return_date", columnList = "return_date")
       }
)
public class ReturnTransaction extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dispense_id", nullable = false)
    private DispenseTransaction dispenseTransaction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "returned_by", nullable = false)
    private User returnedBy;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "quantity_returned", nullable = false)
    private int quantityReturned;

    @Column(name = "reason", length = 1000)
    private String reason;
}
