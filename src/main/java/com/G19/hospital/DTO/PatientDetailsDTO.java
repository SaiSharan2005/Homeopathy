package com.G19.hospital.DTO;

public class PatientDetailsDTO {

    private Long useId;
    // private String patientId;
    private Integer age;
    private String gender;
    private String address;
    private String city;
    private String pincode;

    // Default constructor
    public PatientDetailsDTO() {}

    // Parameterized constructor
    public PatientDetailsDTO(Long useId, Integer age, String gender, String address, String city, String pincode) {
        this.useId = useId;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
    }

    // Getters and Setters

    public Long getUserId() {
        return useId;
    }

    public void setUserId(Long useId) {
        this.useId = useId;
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
}