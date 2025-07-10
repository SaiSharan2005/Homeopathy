package com.G19.hospital.service;

import java.time.LocalDate;

import com.G19.hospital.model.DailyAppointmentSummary;

public interface DailyAppointmentSummaryService {
    DailyAppointmentSummary saveSummary(DailyAppointmentSummary summary);
    DailyAppointmentSummary getSummaryByDate(LocalDate date);

    void generateDailySummary();

    void deleteSummaryById(Long id);
}

