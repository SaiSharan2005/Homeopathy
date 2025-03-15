package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.DoctorScheduleDTO;
import com.G19.hospital.model.User;  // Updated import to User
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.repository.DoctorScheduleRepository;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.service.DoctorScheduleServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorScheduleServicesImpl implements DoctorScheduleServices {

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DoctorTimingRepository doctorTimingRepository;

    @Override
    public List<DoctorSchedule> createScheduleForDate(User doctorId, LocalDate date) { // Updated parameter type to User
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot create a schedule for a past date.");
        }
        
        List<DoctorSchedule> doctorSchedule = new ArrayList<>();
        List<DoctorTiming> doctorSlots = doctorTimingRepository.findByDoctorAndInUse(doctorId, true); // Updated to User
        for (DoctorTiming slot : doctorSlots) {
            DoctorSchedule tempSchedule = new DoctorSchedule();
            tempSchedule.setDoctor(doctorId); // Updated to User
            tempSchedule.setDate(date);
            tempSchedule.setBooked(false);
            tempSchedule.setStartTime(slot.getStartTime());
            tempSchedule.setEndTime(slot.getEndTime());
            tempSchedule.setSlot(slot);
            doctorSchedule.add(tempSchedule);
        }
        return doctorScheduleRepository.saveAll(doctorSchedule);
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

    @Override
    public List<DoctorSchedule> getScheduleByDoctorAndDate(User doctor, LocalDate date) { // Updated parameter type to User
        return doctorScheduleRepository.findByDoctorAndDate(doctor, date); // Updated to User
    }

    @Override
    public DoctorSchedule getScheduleById(Long scheduleId) {
        return doctorScheduleRepository.findByScheduleId(scheduleId);
    }
}
