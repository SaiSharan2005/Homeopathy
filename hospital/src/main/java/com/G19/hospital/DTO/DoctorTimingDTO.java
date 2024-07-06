package com.G19.hospital.DTO;

import java.time.LocalTime;

import com.G19.hospital.model.DoctorRegister;

public class DoctorTimingDTO {
    private Long slotId;
    private DoctorRegister doctorId;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean inUse;


    public DoctorTimingDTO() {}

    // Parameterized constructor
    public DoctorTimingDTO(Long slotId, DoctorRegister doctorId, LocalTime startTime, LocalTime endTime, boolean inUse) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.inUse = inUse;
    }

    // Getters and Setters
    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public DoctorRegister getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorRegister doctorId) {
        this.doctorId = doctorId;
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

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
