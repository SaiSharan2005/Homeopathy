package com.G19.hospital.DTO;

import java.time.LocalDateTime;

public class DoctorScheduleDTO {

    private Long scheduleId;
    private LocalDateTime slots;
    private boolean bookingStatus;
    private Long doctorId;

    // Default constructor
    public DoctorScheduleDTO() {
    }

    // Parameterized constructor
    public DoctorScheduleDTO(Long scheduleId, LocalDateTime slots, boolean bookingStatus, Long doctorId) {
        this.scheduleId = scheduleId;
        this.slots = slots;
        this.bookingStatus = bookingStatus;
        this.doctorId = doctorId;
    }

    // Getters and Setters

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getSlots() {
        return slots;
    }

    public void setSlots(LocalDateTime slots) {
        this.slots = slots;
    }

    public boolean isBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
