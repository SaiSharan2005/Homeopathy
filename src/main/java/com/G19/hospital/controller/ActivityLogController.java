package com.G19.hospital.controller;

import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-log")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    // Create a new ActivityLog
    @PostMapping
    public ResponseEntity<ActivityLog> createActivityLog(@RequestBody ActivityLog activityLog) {
        ActivityLog savedLog = activityLogService.createActivityLog(activityLog);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    // Get all ActivityLogs
    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAllActivityLogs() {
        List<ActivityLog> logs = activityLogService.getAllActivityLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    // Get an ActivityLog by ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        Optional<ActivityLog> activityLog = activityLogService.getActivityLogById(id);
        return activityLog.map(log -> new ResponseEntity<>(log, HttpStatus.OK))
                          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an ActivityLog
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivityLog(@PathVariable Long id, @RequestBody ActivityLog updatedLog) {
        ActivityLog updated = activityLogService.updateActivityLog(id, updatedLog);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an ActivityLog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        Optional<ActivityLog> log = activityLogService.getActivityLogById(id);
        if (log.isPresent()) {
            activityLogService.deleteActivityLog(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
