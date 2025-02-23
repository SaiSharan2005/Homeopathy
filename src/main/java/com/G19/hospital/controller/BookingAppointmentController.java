package com.G19.hospital.controller;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Collections;
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

    // New endpoint: Update prescription image for an existing appointment
    @PutMapping("/{bookingId}/prescription")
    public ResponseEntity<BookingAppointment> updatePrescriptionImage(
            @PathVariable Long bookingId,
            @RequestPart("file") MultipartFile file) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.updatePrescriptionImage(bookingId, file);
            return ResponseEntity.ok(updatedBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Other endpoints remain unchanged

    @PostMapping("/byStaff")
    public ResponseEntity<BookingAppointment> createBookingAppointmentByStaff(@RequestBody BookingAppointmentDTO bookingAppointmentDTO) {
        try {
            BookingAppointment createdBookingAppointment = bookingAppointmentServices.createBookingAppointment(bookingAppointmentDTO);
            return ResponseEntity.ok(createdBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<BookingAppointment> createBookingAppointment(
            @RequestBody BookingAppointmentDTO bookingAppointmentDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User patient = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            bookingAppointmentDTO.setPatientId(patient.getId());
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

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateMissedAppointments() {
        List<BookingAppointment> upcomingAppointments = bookingAppointmentRepository.findUpcomingAppointments();
        LocalDateTime currentTime = LocalDateTime.now();
        for (BookingAppointment appointment : upcomingAppointments) {
            LocalDateTime endTime = LocalDateTime.of(appointment.getScheduleId().getDate(),
                    appointment.getScheduleId().getEndTime());
            if (endTime.isBefore(currentTime)) {
                appointment.setStatus("missed");
                bookingAppointmentRepository.save(appointment);
            }
        }
        System.out.println("Missed appointments updated at midnight");
    }

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

    @GetMapping("/byId/{id}")
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
        User doctor = new User();
        doctor.setId(doctorId);
        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByDoctorId(doctor);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/doctor/my-appointments")
    public ResponseEntity<List<BookingAppointment>> getMyAppointments() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User doctor = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByDoctorId(doctor);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByPatientId(@PathVariable Long patientId) {
        User patient = new User();
        patient.setId(patientId);
        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByPatientId(patient);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/patient/my-appointments")
    public ResponseEntity<List<BookingAppointment>> getPatientAppointments() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User patient = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByPatientId(patient);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByScheduleId(@PathVariable Long scheduleId) {
        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setScheduleId(scheduleId);
        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByScheduleId(doctorSchedule);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<BookingAppointment> getBookingByToken(@PathVariable String token) {
        Optional<BookingAppointment> booking = bookingAppointmentServices.getBookingByToken(token);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public long AppointmentCount() {
        return bookingAppointmentServices.getAppointmentCount();
    }
}
