package com.G19.hospital.service;

import com.G19.hospital.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing ActivityLog entries.
 */
public interface ActivityLogService {
    ActivityLog createActivityLog(ActivityLog activityLog);
    Page<ActivityLog> getAllActivityLogs(Pageable pageable);
    Optional<ActivityLog> getActivityLogById(Long id);
    ActivityLog updateActivityLog(Long id, ActivityLog updatedLog);
    void deleteActivityLog(Long id);
} 