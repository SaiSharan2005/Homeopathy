package com.G19.hospital.service.implement;

import com.G19.hospital.model.Authentication.BookingAppointment;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BookingAppointmentServicesImpl implements BookingAppointmentServices {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Override
    public BookingAppointment createBookingAppointment(BookingAppointment bookingAppointment) throws Exception {
        BookingAppointment savedAppointment = bookingAppointmentRepository.save(bookingAppointment);
    
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        
        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        
        // Generate the token using the formatted date and booking ID
        String uniqueToken = formattedDate + "-" + savedAppointment.getBookingId();
        
        // Assign the token to the booking appointment
        savedAppointment.setToken(uniqueToken);
        
        return bookingAppointmentRepository.save(bookingAppointment);
    }

    @Override
    public BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointment bookingAppointment) throws Exception {
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));

        existingBookingAppointment.setDoctorId(bookingAppointment.getDoctorId());
        existingBookingAppointment.setPatientId(bookingAppointment.getPatientId());
        existingBookingAppointment.setScheduleId(bookingAppointment.getScheduleId());
        existingBookingAppointment.setToken(bookingAppointment.getToken());

        return bookingAppointmentRepository.save(existingBookingAppointment);
    }

    @Override
    public void deleteBookingAppointment(Long bookingId) throws Exception {
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));

        bookingAppointmentRepository.delete(bookingAppointment);
    }

    @Override
    public List<BookingAppointment> getAllBookingAppointments() {
        return bookingAppointmentRepository.findAll();
    }

    @Override
    public BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception {
        return bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));
    }
}
