package com.G19.hospital.controller;

import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.User; // Changed to import User instead of DoctorRegister
import com.G19.hospital.service.DoctorScheduleServices;
import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.repository.UserRepository; // Import UserRepository for fetching User
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAllSlots(@PathVariable Long doctorId) { // Changed from String to Long
        try {
            User doctorData = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found")); // Fetch User object
            List<DoctorSchedule> data = scheduleService.getScheduleByDoctorAndDate(doctorData, LocalDate.now());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<?> getAllSlotsByDate(@PathVariable Long doctorId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) { // Changed from String to Long
        try {
            User doctorData = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found")); // Fetch User object
            List<DoctorSchedule> data = scheduleService.getScheduleByDoctorAndDate(doctorData, date);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("byId/{scheduleId}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable Long scheduleId) {
        DoctorSchedule schedule = scheduleService.getScheduleById(scheduleId);
        if (schedule == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DoctorSchedule>> getAvailableSlots(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DoctorSchedule> availableSlots = scheduleService.getAvailableSlots(date);
        return ResponseEntity.ok(availableSlots);
    }

    @PostMapping("/book/{scheduleId}")
    public ResponseEntity<String> bookSlot(@PathVariable Long scheduleId) {
        try {
            scheduleService.bookSlot(scheduleId);
            return ResponseEntity.ok("Slot booked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book slot: " + e.getMessage());
        }
    }
}
