package com.G19.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User; // Import User instead of DoctorRegister
import com.G19.hospital.model.DoctorSchedule;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingAppointmentRepository extends JpaRepository<BookingAppointment, Long> {
    List<BookingAppointment> findByDoctor(User doctor); // Updated to User
    BookingAppointment findByBookingId(Long bookingId);
    List<BookingAppointment> findBySchedule(DoctorSchedule schedule); // Updated to use schedule directly
    Optional<BookingAppointment> findByToken(String token);
    List<BookingAppointment> findByPatient(User patient); // Updated to use patient directly
    int countByAppointDate(LocalDate date);
    int countByStatusAndAppointDate(String status, LocalDate date); // Updated parameter name for clarity

    @Query("SELECT ba FROM BookingAppointment ba WHERE ba.status = 'upcoming'")
    List<BookingAppointment> findUpcomingAppointments();
}
