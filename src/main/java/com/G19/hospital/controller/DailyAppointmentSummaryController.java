package com.G19.hospital.controller;

import com.G19.hospital.model.DailyAppointmentSummary;
import com.G19.hospital.service.DailyAppointmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/daily-summary")
public class DailyAppointmentSummaryController {

    @Autowired
    private DailyAppointmentSummaryService summaryService;

    // Save a new summary
    @PostMapping("/save")
    public DailyAppointmentSummary saveSummary(@RequestBody DailyAppointmentSummary summary) {
        return summaryService.saveSummary(summary);
    }

    // Get summary by date
    @GetMapping("/date/{date}")
    public DailyAppointmentSummary getSummaryByDate(@PathVariable String date) {
        return summaryService.getSummaryByDate(LocalDate.parse(date));
    }

    // Delete summary by ID
    @DeleteMapping("/delete/{id}")
    public void deleteSummaryById(@PathVariable Long id) {
        summaryService.deleteSummaryById(id);
    }
}
