package com.G19.hospital.DTO;

public class PatientRegisterDTO {

  private Long id;
  private String patientName;
  private String phoneNumber;
  private String email;
  private String password;
  private String patientId; // Consider omitting password for security reasons

  public PatientRegisterDTO() {
  }

  public PatientRegisterDTO(Long id, String patientName, String phoneNumber, String email, String password) {
    this.id = id;
    this.patientName = patientName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password=password;
  }

  // Getters and Setters for all fields
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  public void setEmail(String email) {
    this.email=email;
  }

  public String getEmail() {
    return email;
  }

  

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }


  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }
}
