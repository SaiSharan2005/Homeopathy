package com.G19.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.G19.hospital.model.DoctorSchedule;
import com.G19.hospital.model.User;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorAndDate(User doctor, LocalDate date); // Updated parameter type
    List<DoctorSchedule> findByDateAndBooked(LocalDate date, boolean booked);
    DoctorSchedule findByScheduleId(Long scheduleId);
    boolean existsByDoctorAndBookedFalse(User doctor);
    List<DoctorSchedule> findByBooked(boolean booked);

    @Query("SELECT DISTINCT ds.doctor FROM DoctorSchedule ds WHERE ds.booked = false")
    Page<User> findDistinctDoctorsByBookedFalse(Pageable pageable);

}
