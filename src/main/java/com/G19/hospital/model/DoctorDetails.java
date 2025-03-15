package com.G19.hospital.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_details")
public class DoctorDetails extends BaseEntity {

    @Column(unique = true)
    private String doctorId;


    private Integer age;
    private String gender;
    private String address;
    private String city;
    private String pincode;
    private Double consultationFee;
    private String specialization;
    private Double remuneration;

    // One-to-One relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    // Additional constructors and methods
}
