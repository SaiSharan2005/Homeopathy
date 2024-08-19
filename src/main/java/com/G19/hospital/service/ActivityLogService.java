package com.G19.hospital.service;

import com.G19.hospital.model.ActivityLog;

import java.util.List;
import java.util.Optional;

public interface ActivityLogService {
    ActivityLog createActivityLog(ActivityLog activityLog);
    List<ActivityLog> getAllActivityLogs();
    Optional<ActivityLog> getActivityLogById(Long id);
    ActivityLog updateActivityLog(Long id, ActivityLog activityLog);
    void deleteActivityLog(Long id);
}
