package com.G19.hospital.service;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.PatientRegister;

import java.util.List;
import java.util.Optional;

public interface BookingAppointmentServices {
    BookingAppointment createBookingAppointment(BookingAppointment bookingAppointment) throws Exception;
    BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointment bookingAppointment) throws Exception;
    BookingAppointment completedAppointment(String tokenId) throws Exception;
    
    void cancelBookingAppointment(Long bookingId) throws Exception;
    List<BookingAppointment> getAllBookingAppointments();
    BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception;

     List<BookingAppointment> getBookingsByDoctorId(DoctorRegister doctorId) ;

     List<BookingAppointment> getBookingsByPatientId(PatientRegister patientId) ;

    List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId) ;

    Optional<BookingAppointment> getBookingByToken(String token) ;

    long getAppointmentCount();
}
