package com.G19.hospital.model.inventory.prescription;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.core.AuditableBaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescriptions", indexes = {
        @Index(name = "idx_rx_patient", columnList = "patient_id"),
        @Index(name = "idx_rx_doctor", columnList = "prescribed_by"),
        @Index(name = "idx_rx_date", columnList = "rx_date")
})
public class Prescription extends AuditableBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prescribed_by", nullable = false)
    private User prescribedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingAppointment bookingAppointment;

    @Column(name = "rx_date", nullable = false)
    private LocalDateTime rxDate;

    @Column(name = "notes", length = 1000)
    private String notes;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PrescriptionItem> items = new HashSet<>();

    public void addItem(PrescriptionItem item) {
        item.setPrescription(this);
        this.items.add(item);
    }
}