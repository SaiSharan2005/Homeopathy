package com.G19.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;  // Changed from DoctorRegister to User

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private DoctorTiming slot;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean booked = false;

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public User getDoctor() {
        return doctor;  // Changed return type to User
    }

    public void setDoctor(User doctor) {  // Changed parameter type to User
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public DoctorTiming getSlot(){
        return this.slot;
    }
    
    public void setSlot(DoctorTiming slot){
        this.slot = slot;
    }
}
