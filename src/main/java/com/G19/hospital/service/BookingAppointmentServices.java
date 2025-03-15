package com.G19.hospital.service;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.DoctorSchedule;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

public interface BookingAppointmentServices {
    BookingAppointment createBookingAppointment(BookingAppointmentDTO bookingAppointment) throws Exception;
    BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointmentDTO bookingAppointment) throws Exception;
    BookingAppointment completedAppointment(String tokenId) throws Exception;
    void cancelBookingAppointment(Long bookingId) throws Exception;
    List<BookingAppointment> getAllBookingAppointments();
    BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception;
    List<BookingAppointment> getBookingsByDoctorId(User doctorId);
    List<BookingAppointment> getBookingsByPatientId(User patientId);
    List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId);
    Optional<BookingAppointment> getBookingByToken(String token);
    long getAppointmentCount();
    
    // New method for updating/adding a prescription image for an appointment
    BookingAppointment updatePrescriptionImage(Long bookingId, MultipartFile file) throws Exception;
}

