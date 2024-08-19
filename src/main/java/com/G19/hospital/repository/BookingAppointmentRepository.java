package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.PatientRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingAppointmentRepository extends JpaRepository<BookingAppointment, Long> {
    List<BookingAppointment> findByDoctorId(DoctorRegister doctorId);
    BookingAppointment findByBookingId(Long bookingId);
    List<BookingAppointment> findByScheduleId(DoctorSchedule schedule);
    Optional<BookingAppointment> findByToken(String token);
    List<BookingAppointment> findByPatientId(PatientRegister patientId);
    int countByAppointDate(LocalDate date);
    int countByStatusAndAppointDate(String token , LocalDate date);
    @Query("SELECT ba FROM BookingAppointment ba WHERE ba.status = 'upcoming'")
    List<BookingAppointment> findUpcomingAppointments();
    // long countTodayAppointments(LocalDate today);
}
