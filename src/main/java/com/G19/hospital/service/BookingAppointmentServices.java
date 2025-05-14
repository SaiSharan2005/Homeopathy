package com.G19.hospital.service;

import com.G19.hospital.DTO.BookingAppointmentDTO;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.DoctorSchedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookingAppointmentServices {
    BookingAppointment createBookingAppointment(BookingAppointmentDTO bookingAppointment) throws Exception;
    BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointmentDTO bookingAppointment) throws Exception;
    BookingAppointment completedAppointment(String tokenId) throws Exception;
    void cancelBookingAppointment(Long bookingId) throws Exception;

    // old:
    List<BookingAppointment> getAllBookingAppointments();
    // new:
    Page<BookingAppointment> getAllBookingAppointments(Pageable pageable);

    BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception;

    // old:
    List<BookingAppointment> getBookingsByDoctorId(User doctorId);
    List<BookingAppointment> getBookingsByPatientId(User patientId);
    List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId);
    // new:
    Page<BookingAppointment> getBookingsByDoctorId(User doctorId, Pageable pageable);
    Page<BookingAppointment> getBookingsByPatientId(User patientId, Pageable pageable);
    Page<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId, Pageable pageable);

    Optional<BookingAppointment> getBookingByToken(String token);
    long getAppointmentCount();

    // prescription image:
    BookingAppointment updatePrescriptionImage(Long bookingId, MultipartFile file) throws Exception;

}
