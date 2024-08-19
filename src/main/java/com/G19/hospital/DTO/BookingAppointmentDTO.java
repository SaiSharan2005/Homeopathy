package com.G19.hospital.DTO;

import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.PatientRegister;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

public class BookingAppointmentDTO {

    private Long bookingId;
    @OneToMany
    @JoinColumn(name="doctorId",referencedColumnName = "doctor_id")
    private DoctorRegister doctorId;
    
    @OneToMany
    @JoinColumn(name="patientId",referencedColumnName = "patient_id")
    private PatientRegister patientId;

    @OneToMany
    @JoinColumn(name="scheduleId",referencedColumnName = "Schedule_id")
    private DoctorSchedule scheduleId;
    private String token;

    // Default constructor
    public BookingAppointmentDTO() {
    }

    // Parameterized constructor
    public BookingAppointmentDTO(Long bookingId, DoctorRegister doctorId, PatientRegister patientId, DoctorSchedule scheduleId, String token) {
        this.bookingId = bookingId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.scheduleId = scheduleId;
        this.token = token;
    }

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
