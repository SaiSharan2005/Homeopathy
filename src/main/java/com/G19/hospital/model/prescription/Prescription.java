package com.G19.hospital.model.prescription;

import com.G19.hospital.model.BaseEntity;
import com.G19.hospital.model.User;
import com.G19.hospital.model.BookingAppointment;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {

    @Column(name = "prescription_number", nullable = false, unique = true)
    private String prescriptionNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private BookingAppointment bookingAppointment;


    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;
    
    @Column(name = "general_instructions", length = 500)
    private String generalInstructions;
    
    // Use a unique reference value to fix the bidirectional relationship
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "prescription-items")
    private List<PrescriptionItem> prescriptionItems = new ArrayList<>();
}
