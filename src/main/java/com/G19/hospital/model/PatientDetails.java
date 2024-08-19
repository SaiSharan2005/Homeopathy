package com.G19.hospital.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class PatientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer age;
    private String gender;
    private String address;
    private String city;
    private String pincode;

    @OneToOne
    @JoinColumn(name = "patient_register_id", referencedColumnName = "id")
    @JsonBackReference
    private PatientRegister patientRegister;


    public PatientDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // public String getPatientId() {
    //     return patientId;
    // }

    // public void setPatientId(String patientId) {
    //     this.patientId = patientId;
    // }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public PatientRegister getPatientRegister() {
        return patientRegister;
    }

    public void setPatientRegister(PatientRegister patientRegister) {
        this.patientRegister = patientRegister;
    }
}
