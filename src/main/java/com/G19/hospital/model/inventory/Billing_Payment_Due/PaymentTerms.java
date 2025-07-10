package com.G19.hospital.model.inventory.Billing_Payment_Due;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// 1. PaymentTerms.java
@Entity
@Setter
@Getter
@Table(name = "payment_terms")
public class PaymentTerms {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTermsId;

    @Column(nullable = false)
    private String name;                // e.g. Net30, DueOnReceipt

    @Column(nullable = false)
    private Integer daysUntilDue;
}
