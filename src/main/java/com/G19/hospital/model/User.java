package com.G19.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity {

    @Size(min = 3, message = "Username length must be minimum 6")
    @Column(name = "username", unique = true)
    private String username;

    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 10, message = "Phone number length must be minimum 10")
    @Column(name = "phoneNumber", unique = true, nullable = false)
    private String phoneNumber;

    @Size(min = 8, message = "Password length must be minimum 8")
    @Column(name = "password")
    private String password;

    @Size(min = 4, message = "User ID length must be minimum 4")
    @Column(name = "userId", unique = true)
    private String userId;

    // New field to store the profile picture URL
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    // One-to-One relationships with additional details for doctors and patients
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DoctorDetails doctorDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PatientDetails patientDetails;
}
