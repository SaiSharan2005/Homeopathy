package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.DoctorScheduleDTO;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.repository.DoctorScheduleRepository;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.service.DoctorScheduleServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorScheduleServicesImpl implements DoctorScheduleServices {

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;
    @Autowired
    private DoctorTimingRepository doctorTimingRepository;
    // @Override
    // public void createDailySchedule(DoctorRegister doctor, LocalTime startTime, LocalTime endTime, int slotDurationMinutes) {
    //     LocalDate date = LocalDate.now();
    //     createScheduleForDate(doctor, date, startTime, endTime, slotDurationMinutes);
    // }
    @Override
    public List<DoctorSchedule> createScheduleForDate(DoctorRegister doctorId, LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            // Option 1: Return an empty list
            // return Collections.emptyList();
            
            // Option 2: Throw an exception
            throw new IllegalArgumentException("Cannot create a schedule for a past date.");
        }
        List<DoctorSchedule> doctorSchedule = new ArrayList<>();
        List<DoctorTiming> doctorSlots = doctorTimingRepository.findByDoctorIdAndInUse(doctorId, true);
        for(DoctorTiming slot:doctorSlots){
            DoctorSchedule tempSchedule = new DoctorSchedule();
            tempSchedule.setDoctor(doctorId);
            tempSchedule.setDate(date);
            tempSchedule.setBooked(false);
            tempSchedule.setStartTime(slot.getStartTime());
            tempSchedule.setEndTime(slot.getEndTime());
            tempSchedule.setSlot(slot);
            doctorSchedule.add(tempSchedule);
        }
        return doctorScheduleRepository.saveAll(doctorSchedule);

        // LocalTime slotStart = startTime;
        // while (slotStart.isBefore(endTime)) {
        //     LocalTime slotEnd = slotStart.plusMinutes(slotDurationMinutes);
        //     if (slotEnd.isAfter(endTime)) {
        //         break;
        //     }
        //     DoctorSchedule schedule = new DoctorSchedule();
        //     schedule.setDoctor(doctor);
        //     schedule.setDate(date);
        //     schedule.setStartTime(slotStart);
        //     schedule.setEndTime(slotEnd);
        //     schedule.setBooked(false);
        //     doctorScheduleRepository.save(schedule);
        //     slotStart = slotEnd;
        // }
    }
    @Override
    public List<DoctorSchedule> getAvailableSlots(LocalDate date) {
        return doctorScheduleRepository.findByDateAndBooked(date, false);
    }
    @Override
    public void bookSlot(Long scheduleId) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Slot not found"));
        schedule.setBooked(true);
        doctorScheduleRepository.save(schedule);
    }
    @Override
    public void cancelSlot(Long scheduleId) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Slot not found"));
        schedule.setBooked(false);
        doctorScheduleRepository.save(schedule);
    }

    // @Override
    // public List<LocalTime> generateAppointmentSlots(DoctorRegister doctorId) {
    //     List<LocalTime> appointmentSlots = new ArrayList<>();
    //     LocalTime startTime = LocalTime.of(9, 0);
    //     LocalTime endTime = LocalTime.of(17, 0);
    //     while (startTime.plusMinutes(30).isBefore(endTime)) {

    //         // String slot = startTime + " - " + startTime.plusMinutes(30);
    //         appointmentSlots.add(startTime);
    //         startTime = startTime.plusMinutes(30);
    //     }
    //     return appointmentSlots;
    // }

    @Override
    public List<DoctorSchedule> getScheduleByDoctorAndDate(DoctorRegister doctor,LocalDate date){
        return doctorScheduleRepository.findByDoctorAndDate(doctor, date);
    
    }
    @Override
    public DoctorSchedule getScheduleById(Long scheduleId) {
        return doctorScheduleRepository.findByScheduleId(scheduleId);
    }
}