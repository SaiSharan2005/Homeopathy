package com.G19.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Size(min = 6, message = "Username length must be minimum 6")
    @Column(name = "username", unique = true)
    private String username;

    @Email(message = "Email Should Be Valid")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 10,message = "Email Should Be Valid")
    @Column(name = "phoneNumber", unique = true, nullable = false)
    private String phoneNumber;

    @Size(min = 8, message = "Password length must be minimum 8")
    @Column(name = "password")
    private String password;

    @Size(min = 4, message = "User Id length must be minimum 8")
    @Column(name = "userId")
    private String userId;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    // One-to-One relationships with details entities
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DoctorDetails doctorDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PatientDetails patientDetails;

}