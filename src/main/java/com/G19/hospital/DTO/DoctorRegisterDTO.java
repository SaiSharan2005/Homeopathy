package com.G19.hospital.DTO;

public class DoctorRegisterDTO {

    private String username;
    private String phoneNumber;
    private String password;
    private String email;
    // private DoctorDetailsDTO doctorDetailsDTO; // Add DoctorDetailsDTO field

    // Constructor, getters, and setters

    public DoctorRegisterDTO() {
    }

    public DoctorRegisterDTO(String username, String phoneNumber, String password, String email, DoctorDetailsDTO doctorDetailsDTO) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        // this.doctorDetailsDTO = doctorDetailsDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
