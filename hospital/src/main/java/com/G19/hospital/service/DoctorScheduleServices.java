package com.G19.hospital.service;

import com.G19.hospital.DTOs.DoctorScheduleDTO;
import com.G19.hospital.model.Authentication.DoctorRegister;
import com.G19.hospital.model.Authentication.DoctorSchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface DoctorScheduleServices {
    void createDailySchedule(DoctorRegister doctor, LocalTime startTime, LocalTime endTime, int slotDurationMinutes) ;
    void createScheduleForDate(DoctorRegister doctor, LocalDate date, LocalTime startTime, LocalTime endTime, int slotDurationMinutes) ;
    List<DoctorSchedule> getAvailableSlots(LocalDate date) ;
    void bookSlot(Long scheduleId);
    void cancelSlot(Long scheduleId) ;
    List<LocalTime> generateAppointmentSlots()throws Exception;
    List<DoctorSchedule> getScheduleByDoctorAndDate(DoctorRegister doctor,LocalDate date)throws Exception;
    DoctorSchedule getScheduleById(Long scheduleId) ;
        // DoctorSchedule updateDoctorSchedule(Long scheduleId, DoctorScheduleDTO doctorScheduleDTO) throws Exception;
    // void deleteDoctorSchedule(Long scheduleId) throws Exception;
    // List<DoctorSchedule> getDoctorSchedulesByDoctorId(Long doctorId) throws Exception;
}
