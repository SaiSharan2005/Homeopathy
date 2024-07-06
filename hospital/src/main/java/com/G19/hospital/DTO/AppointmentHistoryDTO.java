package com.G19.hospital.DTO;

public class AppointmentHistoryDTO {

    private Long bookingId;
    private Long doctorId;
    private Long adminId;
    private String role;
    private String action;
    private String reasonForAction;

    // Getters and Setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReasonForAction() {
        return reasonForAction;
    }

    public void setReasonForAction(String reasonForAction) {
        this.reasonForAction = reasonForAction;
    }
}
