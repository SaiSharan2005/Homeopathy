// package com.G19.hospital.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// @Component
// public class DailySummaryScheduler {

//     @Autowired
//     private DailyAppointmentSummaryService summaryService;

//     // This will run the summary generation at midnight every day
//     @Scheduled(cron = "0 * * * * ?") // Every minute
//     public void generateSummaryAtMidnight() {
//         summaryService.generateDailySummary();
//     }
// }


package com.G19.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailySummaryScheduler {

    @Autowired
    private DailyAppointmentSummaryService summaryService;

    // This will run the summary generation at 1 AM every day
    @Scheduled(cron = "0 0 1 * * ?")
    public void generateSummaryAt1AM() {
        summaryService.generateDailySummary();
    }
}
