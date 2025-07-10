package com.G19.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingAppointmentRepository extends JpaRepository<BookingAppointment, Long> {
    // existing:
    List<BookingAppointment> findByDoctor(User doctor);
    List<BookingAppointment> findBySchedule(DoctorSchedule schedule);
    List<BookingAppointment> findByPatient(User patient);
    Optional<BookingAppointment> findByToken(String token);

    // new pageable overloads:
    Page<BookingAppointment> findByDoctor(User doctor, Pageable pageable);
    Page<BookingAppointment> findBySchedule(DoctorSchedule schedule, Pageable pageable);
    Page<BookingAppointment> findByPatient(User patient, Pageable pageable);

    BookingAppointment findByBookingId(Long bookingId);

    int countByAppointDate(LocalDate date);
    int countByStatusAndAppointDate(String status, LocalDate date);

    @Query("SELECT ba FROM BookingAppointment ba WHERE ba.status = 'upcoming'")
    List<BookingAppointment> findUpcomingAppointments();
}
