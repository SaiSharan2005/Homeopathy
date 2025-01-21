package com.G19.hospital.repository;

import com.G19.hospital.model.User; // Import User instead of DoctorRegister
import com.G19.hospital.model.DoctorSchedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorAndDate(User doctor, LocalDate date); // Updated parameter type
    List<DoctorSchedule> findByDateAndBooked(LocalDate date, boolean booked);
    DoctorSchedule findByScheduleId(Long scheduleId);
    boolean existsByDoctorAndBookedFalse(User doctor);
    List<DoctorSchedule> findByBooked(boolean booked);


}
