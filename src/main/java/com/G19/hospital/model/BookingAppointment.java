package com.G19.hospital.model;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookingAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;  // Changed from DoctorRegister to User

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;  // Changed from PatientRegister to User

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private DoctorSchedule schedule;

    @Column(unique = true)
    private String token;

    @Column(unique = false)
    private LocalDate appointDate;

    @ColumnDefault("'Upcoming'")
    private String status = "upcoming";


    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public User getDoctor() {
        return doctor;  // Changed return type to User
    }

    public void setDoctor(User doctor) {  // Changed parameter type to User
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;  // Changed return type to User
    }

    public void setPatient(User patient) {  // Changed parameter type to User
        this.patient = patient;
    }

    public DoctorSchedule getScheduleId() {
        return schedule;
    }

    public void setScheduleId(DoctorSchedule scheduleId) {
        this.schedule = scheduleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getAppointmenDate(){
        return this.appointDate;
    }

    public void setAppointmentDate(LocalDate appointmenDate){
        this.appointDate = appointmenDate;
    }
}
