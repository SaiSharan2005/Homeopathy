package com.G19.hospital.service;

import com.G19.hospital.model.User; // Updated import to User
import com.G19.hospital.model.DoctorSchedule;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleServices {
    List<DoctorSchedule> createScheduleForDate(User doctorId, LocalDate date); // Updated parameter type to User
    List<DoctorSchedule> getAvailableSlots(LocalDate date);
    void bookSlot(Long scheduleId);
    void cancelSlot(Long scheduleId);
    List<DoctorSchedule> getScheduleByDoctorAndDate(User doctor, LocalDate date) throws Exception; // Updated parameter type to User
    DoctorSchedule getScheduleById(Long scheduleId);
}
