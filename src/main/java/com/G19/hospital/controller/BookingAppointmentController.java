package com.G19.hospital.controller;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User; // Updated import to User
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookingAppointments")
public class BookingAppointmentController {

    @Autowired
    private BookingAppointmentServices bookingAppointmentServices;

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private UserRepository userRepository;

//  @PostMapping
//     public ResponseEntity<BookingAppointment> createBookingAppointment(@RequestBody BookingAppointmentDTO bookingAppointmentDTO) {
//         try {
//             BookingAppointment createdBookingAppointment = bookingAppointmentServices.createBookingAppointment(bookingAppointmentDTO);
//             return ResponseEntity.ok(createdBookingAppointment);
//         } catch (Exception e) {
//             return ResponseEntity.badRequest().build();
//         }
//     }

@PostMapping
public ResponseEntity<BookingAppointment> createBookingAppointment(@RequestBody BookingAppointmentDTO bookingAppointmentDTO) {
    try {
        // Extract the authenticated user's details from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the doctor's identifier

        // Fetch the doctor (User object) by username
        User patient = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Set the doctor ID in the DTO
        bookingAppointmentDTO.setPatientId(patient.getId());

        // Create the booking appointment
        BookingAppointment createdBookingAppointment = bookingAppointmentServices.createBookingAppointment(bookingAppointmentDTO);

        return ResponseEntity.ok(createdBookingAppointment);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<BookingAppointment> updateBookingAppointment(@PathVariable Long id,
            @RequestBody BookingAppointmentDTO bookingAppointmentDTO) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.updateBookingAppointment(id, bookingAppointmentDTO);
            return ResponseEntity.ok(updatedBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Runs every midnight
    public void updateMissedAppointments() {
        List<BookingAppointment> upcomingAppointments = bookingAppointmentRepository.findUpcomingAppointments();
        LocalDateTime currentTime = LocalDateTime.now(); // Get the current time

        for (BookingAppointment appointment : upcomingAppointments) {
            LocalDateTime endTime = LocalDateTime.of(appointment.getScheduleId().getDate(), appointment.getScheduleId().getEndTime());

            if (endTime.isBefore(currentTime)) { // Compare LocalDateTime objects
                appointment.setStatus("missed");
                bookingAppointmentRepository.save(appointment);
            }
        }

        System.out.println("Missed appointments updated at midnight");
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<BookingAppointment> updateBookingAppointment(@PathVariable Long id,
    //         @RequestBody BookingAppointment bookingAppointment) {
    //     try {
    //         BookingAppointment updatedBookingAppointment = bookingAppointmentServices.updateBookingAppointment(id,
    //                 bookingAppointment);
    //         return ResponseEntity.ok(updatedBookingAppointment);
    //     } catch (Exception e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping("/completed-appointment/{token}")
    public ResponseEntity<BookingAppointment> completedAppointment(@PathVariable String token) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.completedAppointment(token);
            return ResponseEntity.ok(updatedBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBookingAppointment(@PathVariable Long id) {
        try {
            bookingAppointmentServices.cancelBookingAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingAppointment>> getAllBookingAppointments() {
        List<BookingAppointment> bookingAppointments = bookingAppointmentServices.getAllBookingAppointments();
        return ResponseEntity.ok(bookingAppointments);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<BookingAppointment> getBookingAppointmentById(@PathVariable Long id) {
        try {
            BookingAppointment bookingAppointment = bookingAppointmentServices.getBookingAppointmentById(id);
            return ResponseEntity.ok(bookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByDoctorId(@PathVariable Long doctorId) {
        // Assuming doctorId is Long type
        User doctor = new User();
        doctor.setId(doctorId); // Set the doctorId to the User entity

        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByDoctorId(doctor);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/my-appointments")
pubulic ResponseEntity<List<BookingAppointment>> getMyAppointments() {
    try {
        // Extract the authenticated user's details from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the doctor's identifier

        // Fetch the doctor (User object) by username
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Fetch the bookings for the authenticated doctor
        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByDoctorId(doctor);

        return ResponseEntity.ok(bookings);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.emptyList());
    }
}


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByPatientId(@PathVariable Long patientId) {
        // Assuming patientId is Long type
        User patient = new User();
        patient.setId(patientId); // Set the patientId to the User entity

        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByPatientId(patient);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByScheduleId(@PathVariable Long scheduleId) {
        // Assuming scheduleId is Long type
        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setScheduleId(scheduleId); // Set the scheduleId to the DoctorSchedule entity

        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByScheduleId(doctorSchedule);
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/token/{token}")
    public ResponseEntity<BookingAppointment> getBookingByToken(@PathVariable String token) {
        Optional<BookingAppointment> booking = bookingAppointmentServices.getBookingByToken(token);
        return booking.map(ResponseEntity::ok)  // If present, return 200 OK with the BookingAppointment
                      .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 Not Found
    }
    
    @GetMapping("/count")
    public long AppointmentCount() {
        return bookingAppointmentServices.getAppointmentCount();
    }
}
