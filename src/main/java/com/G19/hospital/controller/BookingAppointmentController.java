package com.G19.hospital.controller;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.PatientRegister;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/bookingAppointments")
public class BookingAppointmentController {

    @Autowired
    private BookingAppointmentServices bookingAppointmentServices;

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @PostMapping
    public ResponseEntity<BookingAppointment> createBookingAppointment(
            @RequestBody BookingAppointment bookingAppointment) {
        try {
            BookingAppointment createdBookingAppointment = bookingAppointmentServices
                    .createBookingAppointment(bookingAppointment);
            return ResponseEntity.ok(createdBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

   
    @Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(cron = "0 * * * * ?") // Every minute
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

    
    @PutMapping("/{id}")
    public ResponseEntity<BookingAppointment> updateBookingAppointment(@PathVariable Long id,
            @RequestBody BookingAppointment bookingAppointment) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.updateBookingAppointment(id,
                    bookingAppointment);
            return ResponseEntity.ok(updatedBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/completed-appointment/{id}")
    public ResponseEntity<BookingAppointment> completedAppointment(@PathVariable String id) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.completedAppointment(id);
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
        DoctorRegister doctorRegister = new DoctorRegister();
        doctorRegister.setId(doctorId); // Set the doctorId to the DoctorRegister entity

        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByDoctorId(doctorRegister);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BookingAppointment>> getBookingsByPatientId(@PathVariable Long patientId) {
        // Assuming patientId is Long type
        PatientRegister patientRegister = new PatientRegister();
        patientRegister.setId(patientId); // Set the patientId to the PatientRegister entity

        List<BookingAppointment> bookings = bookingAppointmentServices.getBookingsByPatientId(patientRegister);
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
    public ResponseEntity<Optional<BookingAppointment>> getBookingByToken(@PathVariable String token) {
        Optional<BookingAppointment> booking = bookingAppointmentServices.getBookingByToken(token);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public long AppointmentCount() {
        return bookingAppointmentServices.getAppointmentCount();
    }
    
}
