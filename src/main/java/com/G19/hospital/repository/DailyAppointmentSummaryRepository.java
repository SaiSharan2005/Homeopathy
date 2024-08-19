package com.G19.hospital.repository;

import com.G19.hospital.model.DailyAppointmentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DailyAppointmentSummaryRepository extends JpaRepository<DailyAppointmentSummary, Long> {
    DailyAppointmentSummary findByDate(LocalDate date);
}
