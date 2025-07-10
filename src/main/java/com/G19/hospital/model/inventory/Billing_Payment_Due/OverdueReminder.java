
package com.G19.hospital.model.inventory.Billing_Payment_Due;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// 6. OverdueReminder.java
@Entity
@Setter
@Getter
@Table(name = "overdue_reminders")
public class OverdueReminder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(nullable = false)
    private LocalDateTime sentDate;

    @Column(nullable = false)
    private String reminderType; // Email, SMS

    @Column(length = 1000)
    private String notes;
}
