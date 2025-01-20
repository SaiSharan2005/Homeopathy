package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User; // Updated import to User
import com.G19.hospital.model.DoctorSchedule;
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

@Service
public class BookingAppointmentServicesImpl implements BookingAppointmentServices {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private DoctorScheduleServices doctorScheduleServices;

   @Override
    public BookingAppointment createBookingAppointment(BookingAppointmentDTO bookingAppointmentDTO) throws Exception {
        DoctorSchedule schedule = doctorScheduleServices.getScheduleById(bookingAppointmentDTO.getScheduleId());
        User doctor = new User();
        doctor.setId(bookingAppointmentDTO.getDoctorId());
        User patient = new User();
        patient.setId(bookingAppointmentDTO.getPatientId());

        BookingAppointment bookingAppointment = new BookingAppointment();
        bookingAppointment.setDoctor(doctor);
        bookingAppointment.setPatient(patient);
        bookingAppointment.setScheduleId(schedule);
        bookingAppointment.setAppointmentDate(schedule.getDate());
        bookingAppointment.setStatus(bookingAppointmentDTO.getStatus());
        bookingAppointment = bookingAppointmentRepository.save(bookingAppointment);
        // Generate the token
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        String uniqueToken = formattedDate + "-" + bookingAppointment.getBookingId();
        bookingAppointment.setToken(uniqueToken);

        doctorScheduleServices.bookSlot(schedule.getScheduleId());

        return bookingAppointment;
    }

    @Override
    public BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointmentDTO bookingAppointmentDTO) throws Exception {
        BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking appointment not found"));

        DoctorSchedule schedule = doctorScheduleServices.getScheduleById(bookingAppointmentDTO.getScheduleId());
        existingBookingAppointment.setScheduleId(schedule);

        User doctor = new User();
        doctor.setId(bookingAppointmentDTO.getDoctorId());
        User patient = new User();
        patient.setId(bookingAppointmentDTO.getPatientId());

        existingBookingAppointment.setDoctor(doctor);
        existingBookingAppointment.setPatient(patient);
        existingBookingAppointment.setStatus(bookingAppointmentDTO.getStatus());

        doctorScheduleServices.cancelSlot(existingBookingAppointment.getScheduleId().getScheduleId());
        doctorScheduleServices.bookSlot(schedule.getScheduleId());

        return bookingAppointmentRepository.save(existingBookingAppointment);
    }


    // @Override
    // public BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointment bookingAppointment) throws Exception {
    //     BookingAppointment existingBookingAppointment = bookingAppointmentRepository.findById(bookingId)
    //             .orElseThrow(() -> new Exception("Booking appointment not found"));

    //     DoctorSchedule schedule = existingBookingAppointment.getScheduleId();
    //     doctorScheduleServices.cancelSlot(schedule.getScheduleId());
        
    //     // Update the schedule ID
    //     schedule = bookingAppointment.getScheduleId();
    //     doctorScheduleServices.bookSlot(schedule.getScheduleId());
    //     existingBookingAppointment.setScheduleId(schedule);
        
    //     return bookingAppointmentRepository.save(existingBookingAppointment);
    // }

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
        bookingAppointment.setStatus("cancel");  // Update status to 'cancel'
        DoctorSchedule schedule = bookingAppointment.getScheduleId();
        
        // Cancel the slot in the doctor's schedule
        doctorScheduleServices.cancelSlot(schedule.getScheduleId());
        
        // Save the updated booking appointment
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
    public List<BookingAppointment> getBookingsByDoctorId(User doctorId) { // Updated parameter type to User
        return bookingAppointmentRepository.findByDoctor(doctorId);
    }

    @Override
    public List<BookingAppointment> getBookingsByPatientId(User patientId) { // Updated parameter type to User
        return bookingAppointmentRepository.findByPatient(patientId);
    }

    @Override
    public List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId) {
        return bookingAppointmentRepository.findBySchedule(scheduleId);
    }

    @Override
    public Optional<BookingAppointment> getBookingByToken(String token) {
        return bookingAppointmentRepository.findByToken(token);
    }

    @Override
    public long getAppointmentCount() {
        return bookingAppointmentRepository.countByAppointDate(LocalDate.now());
    }
}
