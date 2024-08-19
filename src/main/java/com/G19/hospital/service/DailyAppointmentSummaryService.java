package com.G19.hospital.service;

import com.G19.hospital.model.DailyAppointmentSummary;

import java.time.LocalDate;

public interface DailyAppointmentSummaryService {
    DailyAppointmentSummary saveSummary(DailyAppointmentSummary summary);
    DailyAppointmentSummary getSummaryByDate(LocalDate date);

    void generateDailySummary();

    void deleteSummaryById(Long id);
}

