package com.G19.hospital.controller;

import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing activity logs with paging support.
 */
@RestController
@RequestMapping("/api/activity-log")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    /**
     * Create a new ActivityLog entry.
     */
    @PostMapping
    public ResponseEntity<ActivityLog> createActivityLog(@RequestBody ActivityLog activityLog) {
        ActivityLog savedLog = activityLogService.createActivityLog(activityLog);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    /**
     * Fetch paged list of ActivityLog entries.
     * Supports optional page, size, and sort parameters.
     */
    @GetMapping
    public ResponseEntity<Page<ActivityLog>> getAllActivityLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // create a pageable with default sorting by timestamp desc
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        // fetch the paged data
        Page<ActivityLog> logsPage = activityLogService.getAllActivityLogs(pageable);

        return new ResponseEntity<>(logsPage, HttpStatus.OK);
    }

    /**
     * Get a single ActivityLog by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        Optional<ActivityLog> log = activityLogService.getActivityLogById(id);
        return log.map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update an existing ActivityLog entry.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivityLog(
            @PathVariable Long id,
            @RequestBody ActivityLog updatedLog) {
        ActivityLog updated = activityLogService.updateActivityLog(id, updatedLog);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an ActivityLog entry by ID.
     */
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
