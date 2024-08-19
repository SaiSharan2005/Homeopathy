package com.G19.hospital.service.implement;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.PatientRegister;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.service.BookingAppointmentServices;
import com.G19.hospital.service.DoctorScheduleServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingAppointmentServicesImpl implements BookingAppointmentServices {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

    @Override
    public BookingAppointment createBookingAppointment(BookingAppointment bookingAppointment) throws Exception {
        DoctorSchedule sch = bookingAppointment.getScheduleId();
        bookingAppointment.setAppointmentDate(sch.getDate());
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
        doctorScheduleServices.bookSlot(sch.getScheduleId());
        
        
        return bookingAppointmentRepository.save(bookingAppointment);
    }

    @Override
    public BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointment bookingAppointment) throws Exception {
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));

        // existingBookingAppointment.setDoctorId(bookingAppointment.getDoctorId());
        // existingBookingAppointment.setPatientId(bookingAppointment.getPatientId());
        // existingBookingAppointment.setToken(bookingAppointment.getToken());
        DoctorSchedule schedule = existingBookingAppointment.getScheduleId();
        doctorScheduleServices.cancelSlot(schedule.getScheduleId());
        schedule = bookingAppointment.getScheduleId();
        doctorScheduleServices.bookSlot(schedule.getScheduleId());
        existingBookingAppointment.setScheduleId(bookingAppointment.getScheduleId());
        return bookingAppointmentRepository.save(existingBookingAppointment);
    }
    @Override
    public BookingAppointment completedAppointment(String tokenId) throws Exception {
        // Find the booking appointment using the token
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findByToken(tokenId)
            .orElseThrow(() -> new Exception("Booking appointment not found"));
    
        // Update the status to 'completed'
        existingBookingAppointment.setStatus("completed");
    
        // Save the updated booking appointment back to the repository
        return bookingAppointmentRepository.save(existingBookingAppointment);
    }
    

    @Override
    public void cancelBookingAppointment(Long bookingId) throws Exception {
        // Retrieve the booking appointment from the repository
        BookingAppointment bookingAppointment = bookingAppointmentRepository.findByBookingId(bookingId);
        bookingAppointment.setStatus("cancel")  ;     // Retrieve 06the schedule ID from the booking appointment
        DoctorSchedule schedule = bookingAppointment.getScheduleId();
        
        // Cancel the slot in the doctor's schedule
        doctorScheduleServices.cancelSlot(schedule.getScheduleId());
        

        
        // Delete the booking appointment
        bookingAppointmentRepository.save(bookingAppointment);
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
    @Override
    public List<BookingAppointment> getBookingsByDoctorId(DoctorRegister doctorId) {
        return bookingAppointmentRepository.findByDoctorId(doctorId);
    }
    @Override
    public List<BookingAppointment> getBookingsByPatientId(PatientRegister patientId) {
        return bookingAppointmentRepository.findByPatientId(patientId);
    }
    @Override
    public List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId) {
        return bookingAppointmentRepository.findByScheduleId(scheduleId);
    }
    @Override
    public Optional<BookingAppointment> getBookingByToken(String token) {
        return bookingAppointmentRepository.findByToken(token);
    }

    @Override
    public long getAppointmentCount(){
        return bookingAppointmentRepository.countByAppointDate(LocalDate.now());
    }
}
