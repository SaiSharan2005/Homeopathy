

package com.G19.hospital.model.inventory.Billing_Payment_Due;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.G19.hospital.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
// 5. Payment.java (updated)
@Entity
@Setter
@Getter
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column
    private String transactionRef;

    @Column
    private String receiptImageUrl;

    @ManyToOne
    @JoinColumn(name = "receipt_uploaded_by")
    private User receiptUploadedBy;

    @Column
    private LocalDateTime receiptUploadDate;

}