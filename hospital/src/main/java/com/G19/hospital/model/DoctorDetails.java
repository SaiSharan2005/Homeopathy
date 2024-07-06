package com.G19.hospital.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class DoctorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;
    private String gender;
    private String address;
    private String city;
    private String pincode;
    private Double consultationFee;
    private String specialization;
    private Double remuneration;

    @OneToOne
    @JoinColumn(name = "doctor_register_id", referencedColumnName = "id")
    @JsonBackReference
    private DoctorRegister doctorRegister;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Double getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(Double remuneration) {
        this.remuneration = remuneration;
    }

    public DoctorRegister getDoctorRegister() {
        return doctorRegister;
    }

    public void setDoctorRegister(DoctorRegister doctorRegister) {
        this.doctorRegister = doctorRegister;
    }
}
