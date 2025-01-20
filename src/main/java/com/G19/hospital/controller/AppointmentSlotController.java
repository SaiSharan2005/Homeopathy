package com.G19.hospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.G19.hospital.model.User; // Updated to use User
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.service.DoctorScheduleServices;
import com.G19.hospital.service.DoctorServices;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/create-appointment-slots")
public class AppointmentSlotController {

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

    @Autowired
    private DoctorServices doctorServices;

    @PostMapping("/date/{date}")
    public ResponseEntity<List<DoctorSchedule>> createAppointmentSlots(
        @RequestBody User user, // Change from DoctorRegister to User
        @PathVariable String date) { // Keep date as String to parse it

        LocalDate parsedDate; // Variable to hold the parsed date
        try {
            parsedDate = LocalDate.parse(date); // Parse the date from the URL
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Handle invalid date format
        }

        try {
            // Create appointment slots based on user information and parsed date
            List<DoctorSchedule> schedules = doctorScheduleServices.createScheduleForDate(user, parsedDate);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            // Log the exception (consider using a logger)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle error response as needed
        }
    }
}
