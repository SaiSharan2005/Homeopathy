package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Authentication.BookingAppointment;
import com.G19.hospital.model.Authentication.DoctorRegister;
import com.G19.hospital.model.Authentication.DoctorSchedule;
import com.G19.hospital.model.Authentication.PatientRegister;
import java.util.List;

public interface BookingAppointmentRepository extends JpaRepository<BookingAppointment, Long> {
       List<BookingAppointment> findByDoctorId(DoctorRegister doctorId);
       BookingAppointment findByBookingId(Long bookingId);
    List<BookingAppointment> findByPatientId(PatientRegister patientId);

    List<BookingAppointment> findByScheduleId(DoctorSchedule scheduleId);

    BookingAppointment findByToken(String token);
}

