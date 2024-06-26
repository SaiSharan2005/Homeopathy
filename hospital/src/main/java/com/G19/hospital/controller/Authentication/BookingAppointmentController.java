package com.G19.hospital.controller.Authentication;

import com.G19.hospital.model.Authentication.BookingAppointment;
import com.G19.hospital.service.BookingAppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookingAppointments")
public class BookingAppointmentController {

    @Autowired
    private BookingAppointmentServices bookingAppointmentServices;

    @PostMapping
    public ResponseEntity<BookingAppointment> createBookingAppointment(@RequestBody BookingAppointment bookingAppointment) {
        try {
            BookingAppointment createdBookingAppointment = bookingAppointmentServices.createBookingAppointment(bookingAppointment);
            return ResponseEntity.ok(createdBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingAppointment> updateBookingAppointment(@PathVariable Long id, @RequestBody BookingAppointment bookingAppointment) {
        try {
            BookingAppointment updatedBookingAppointment = bookingAppointmentServices.updateBookingAppointment(id, bookingAppointment);
            return ResponseEntity.ok(updatedBookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingAppointment(@PathVariable Long id) {
        try {
            bookingAppointmentServices.deleteBookingAppointment(id);
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

    @GetMapping("/{id}")
    public ResponseEntity<BookingAppointment> getBookingAppointmentById(@PathVariable Long id) {
        try {
            BookingAppointment bookingAppointment = bookingAppointmentServices.getBookingAppointmentById(id);
            return ResponseEntity.ok(bookingAppointment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
