package com.G19.hospital.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.G19.hospital.model.User;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.service.DoctorScheduleServices;
// import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.repository.UserRepository; // Assuming you have a UserRepository to fetch user data

@RestController
@RequestMapping("/api/create-appointment-slots")
public class AppointmentSlotController {

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

    // @Autowired
    // private DoctorServices doctorServices;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository to fetch doctor data

    @PostMapping("/date/{date}")
    public ResponseEntity<List<DoctorSchedule>> createAppointmentSlots(
        @PathVariable String date // Date as a path variable
        // @RequestBody User user // User payload in the request body
    ) {
        try {
            // Extract the authenticated user's details from the token
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Assuming username is the unique identifier

            // Fetch the doctor (User object) by username
            User doctorData = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // Parse the date from the URL
            LocalDate parsedDate = LocalDate.parse(date);

            // Create appointment slots based on doctor and parsed date
            List<DoctorSchedule> schedules = doctorScheduleServices.createScheduleForDate(doctorData, parsedDate);

            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            // Handle error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
