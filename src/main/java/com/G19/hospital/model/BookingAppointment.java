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
    private DoctorRegister doctorId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientRegister patientId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private DoctorSchedule scheduleId;
    
    @Column(unique = true)
    private String token;

    @Column(unique = false )
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

    // Remove or update this method
    public BookingAppointment orElseThrow(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
