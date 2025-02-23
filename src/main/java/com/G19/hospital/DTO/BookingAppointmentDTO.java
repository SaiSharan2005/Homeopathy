package com.G19.hospital.DTO;

import java.time.LocalDate;

public class BookingAppointmentDTO {

    private Long doctorId;
    private Long patientId;
    private Long scheduleId;
    private LocalDate appointDate;
    private String status = "Upcoming";

    // Optional field for prescription image URL (mainly used in responses)
    private String prescriptionImageUrl;

    // Getters and Setters
    public Long getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public Long getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    public LocalDate getAppointDate() {
        return appointDate;
    }
    public void setAppointDate(LocalDate appointDate) {
        this.appointDate = appointDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPrescriptionImageUrl() {
        return prescriptionImageUrl;
    }
    public void setPrescriptionImageUrl(String prescriptionImageUrl) {
        this.prescriptionImageUrl = prescriptionImageUrl;
    }
}
