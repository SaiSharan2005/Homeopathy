package com.G19.hospital.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescriptions")
public class Prescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Many prescriptions can belong to one patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
    
    // Many prescriptions can be created by one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;
    
    // Link to the appointment for which this prescription was issued
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private BookingAppointment appointment;
    
    // URL of the prescription image stored on Cloudinary
    @Column(nullable = false)
    private String imageUrl;
    
    // Optional description for the prescription
    private String description;
}
