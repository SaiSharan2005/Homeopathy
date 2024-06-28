package com.G19.hospital.service;

import com.G19.hospital.model.Authentication.BookingAppointment;
import com.G19.hospital.model.Authentication.DoctorRegister;
import com.G19.hospital.model.Authentication.DoctorSchedule;
import com.G19.hospital.model.Authentication.PatientRegister;

import java.util.List;

public interface BookingAppointmentServices {
    BookingAppointment createBookingAppointment(BookingAppointment bookingAppointment) throws Exception;
    BookingAppointment updateBookingAppointment(Long bookingId, BookingAppointment bookingAppointment) throws Exception;
    void deleteBookingAppointment(Long bookingId) throws Exception;
    List<BookingAppointment> getAllBookingAppointments();
    BookingAppointment getBookingAppointmentById(Long bookingId) throws Exception;

     List<BookingAppointment> getBookingsByDoctorId(DoctorRegister doctorId) ;

     List<BookingAppointment> getBookingsByPatientId(PatientRegister patientId) ;

    List<BookingAppointment> getBookingsByScheduleId(DoctorSchedule scheduleId) ;

    BookingAppointment getBookingByToken(String token) ;
}
