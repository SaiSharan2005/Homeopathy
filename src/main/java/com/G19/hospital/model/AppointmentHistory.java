package com.G19.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AppointmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingAppointment bookingAppointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;  // Changed from DoctorRegister to User

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true)
    private User adminId;

    private String role;
    private String action;
    private String reasonForAction;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingAppointment getBookingAppointment() {
        return bookingAppointment;
    }

    public void setBookingAppointment(BookingAppointment bookingAppointment) {
        this.bookingAppointment = bookingAppointment;
    }

    public User getDoctor() {  // Changed return type to User
        return doctor;
    }

    public void setDoctor(User doctor) {  // Changed parameter type to User
        this.doctor = doctor;
    }

    public User getAdminId() {
        return adminId;
    }

    public void setAdminId(User adminId) {
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
