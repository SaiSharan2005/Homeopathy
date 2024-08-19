package com.G19.hospital.DTO;

public class DoctorRegisterDTO {

    private String doctorName;
    private String phoneNumber;
    private String password;
    private String email;
    // private DoctorDetailsDTO doctorDetailsDTO; // Add DoctorDetailsDTO field

    // Constructor, getters, and setters

    public DoctorRegisterDTO() {
    }

    public DoctorRegisterDTO(String doctorName, String phoneNumber, String password, String email, DoctorDetailsDTO doctorDetailsDTO) {
        this.doctorName = doctorName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        // this.doctorDetailsDTO = doctorDetailsDTO;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public DoctorDetailsDTO getDoctorDetailsDTO() {
    //     return doctorDetailsDTO;
    // }

    // public void setDoctorDetailsDTO(DoctorDetailsDTO doctorDetailsDTO) {
    //     this.doctorDetailsDTO = doctorDetailsDTO;
    // }
}
