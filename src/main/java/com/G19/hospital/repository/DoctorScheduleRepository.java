package com.G19.hospital.repository;

import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorSchedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorAndDate(DoctorRegister doctor, LocalDate date);
    List<DoctorSchedule> findByDateAndBooked(LocalDate date, boolean booked);
    // List<DoctorSchedule> findByDoctor( doctorId );
    DoctorSchedule findByScheduleId(Long scheduleId);

}
