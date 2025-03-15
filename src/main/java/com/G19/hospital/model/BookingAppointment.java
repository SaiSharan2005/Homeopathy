package com.G19.hospital.model;

import java.time.LocalDate;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;

@Entity
public class BookingAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private DoctorSchedule schedule;

    @Column(unique = true)
    private String token;

    private LocalDate appointDate;

    @ColumnDefault("'Upcoming'")
    private String status = "upcoming";

    // New field for prescription image URL
    @Column(name = "prescription_image_url")
    private String prescriptionImageUrl;

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public User getDoctor() {
        return doctor;
    }
    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
    public User getPatient() {
        return patient;
    }
    public void setPatient(User patient) {
        this.patient = patient;
    }
    public DoctorSchedule getScheduleId() {
        return schedule;
    }
    public void setScheduleId(DoctorSchedule schedule) {
        this.schedule = schedule;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public LocalDate getAppointmenDate() {
        return this.appointDate;
    }
    public void setAppointmentDate(LocalDate appointmenDate) {
        this.appointDate = appointmenDate;
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
