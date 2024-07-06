package com.G19.hospital.model;

import jakarta.persistence.*;

@Entity
public class BookingAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorRegister doctorId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientRegister patientId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private DoctorSchedule scheduleId;
    
    private String token;

    // Getters and Setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public DoctorRegister getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorRegister doctorId) {
        this.doctorId = doctorId;
    }

    public PatientRegister getPatientId() {
        return patientId;
    }

    public void setPatientId(PatientRegister patientId) {
        this.patientId = patientId;
    }

    public DoctorSchedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(DoctorSchedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
