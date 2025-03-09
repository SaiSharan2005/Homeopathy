package com.G19.hospital.model.prescription;

import com.G19.hospital.model.BaseEntity;
import com.G19.hospital.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {

    // A unique code for each prescription (useful for audit and reference)
    @Column(name = "prescription_number", nullable = false, unique = true)
    private String prescriptionNumber;
    
    // Reference to the doctor who issues the prescription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    // Reference to the patient for whom the prescription is issued
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingAppointment bookingAppointment;

    
    // Date and time when the prescription was issued
    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;
    
    // General instructions or notes relevant to the prescription (e.g., overall lifestyle advice)
    @Column(name = "general_instructions", length = 500)
    private String generalInstructions;
    
    // One-to-many relationship to capture multiple prescribed remedies
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> prescriptionItems = new ArrayList<>();
    
    // Additional constructors, helper methods, or business logic can be added here.
}
