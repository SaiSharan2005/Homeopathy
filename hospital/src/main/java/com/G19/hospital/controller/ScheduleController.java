package com.G19.hospital.controller;

import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.service.DoctorScheduleServices;
import com.G19.hospital.service.DoctorServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private DoctorServices doctorServices;

    @Autowired
    private DoctorScheduleServices scheduleService;

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAllSlots(@PathVariable String doctorId) {
        try {
            DoctorRegister doctorData = doctorServices.getDoctorByDoctorId(doctorId);
            if (doctorData == null) {
                return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
            }
            List<DoctorSchedule> data = scheduleService.getScheduleByDoctorAndDate(doctorData,LocalDate.now());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<?> getAllSlotsByDate(@PathVariable String doctorId, @PathVariable LocalDate date) {
        try {
            DoctorRegister doctorData = doctorServices.getDoctorByDoctorId(doctorId);
            if (doctorData == null) {
                return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
            }
            List<DoctorSchedule> data = scheduleService.getScheduleByDoctorAndDate(doctorData,date);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("byId/{scheduleId}")
    public DoctorSchedule getScheduleById(@PathVariable Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DoctorSchedule>> getAvailableSlots(@RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<DoctorSchedule> availableSlots = scheduleService.getAvailableSlots(date);
        return ResponseEntity.ok(availableSlots);
    }

    @PostMapping("/book/{scheduleId}")
    public ResponseEntity<String> bookSlot(@PathVariable Long scheduleId) {
        try {
            scheduleService.bookSlot(scheduleId);
            return ResponseEntity.ok("Slot booked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to book slot: " + e.getMessage());
        }
    }
    
}
