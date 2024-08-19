package com.G19.hospital.service.implement;

import com.G19.hospital.model.DailyAppointmentSummary;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.DailyAppointmentSummaryRepository;
import com.G19.hospital.service.DailyAppointmentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyAppointmentSummaryServiceImpl implements DailyAppointmentSummaryService {

    @Autowired
    private BookingAppointmentRepository bookingAppointmentRepository;

    @Autowired
    private DailyAppointmentSummaryRepository summaryRepository;

    @Override
    public DailyAppointmentSummary saveSummary(DailyAppointmentSummary summary) {
        return summaryRepository.save(summary);
    }


    @Override
    public DailyAppointmentSummary getSummaryByDate(LocalDate date) {
        DailyAppointmentSummary summary = summaryRepository.findByDate(date);
        return summary;  
}

    @Override
    public void generateDailySummary() {
        LocalDate today = LocalDate.now();
        
        int totalAppointments = bookingAppointmentRepository.countByAppointDate(today);
        int missedAppointments = bookingAppointmentRepository.countByStatusAndAppointDate("Missed", today);
        int completedAppointments = bookingAppointmentRepository.countByStatusAndAppointDate("Completed", today);
        int canceledAppointments = bookingAppointmentRepository.countByStatusAndAppointDate("Canceled", today);

        DailyAppointmentSummary summary = new DailyAppointmentSummary();
        summary.setDate(today);
        summary.setTotalAppointments(totalAppointments);
        summary.setMissedAppointments(missedAppointments);
        summary.setCompletedAppointments(completedAppointments);
        summary.setCanceledAppointments(canceledAppointments);

        summaryRepository.save(summary);
    }

    @Override
    public void deleteSummaryById(Long id) {
        summaryRepository.deleteById(id);
    }
}



