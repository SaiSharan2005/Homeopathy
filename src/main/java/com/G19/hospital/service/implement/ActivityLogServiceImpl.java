package com.G19.hospital.service.implement;

import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.repository.ActivityLogRepository;
import com.G19.hospital.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of ActivityLogService using ActivityLogRepository.
 */
@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public ActivityLog createActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    @Override
    public Page<ActivityLog> getAllActivityLogs(Pageable pageable) {
        return activityLogRepository.findAll(pageable);
    }

    @Override
    public Optional<ActivityLog> getActivityLogById(Long id) {
        return activityLogRepository.findById(id);
    }

    @Override
    public ActivityLog updateActivityLog(Long id, ActivityLog updatedLog) {
        return activityLogRepository.findById(id)
            .map(existing -> {
                existing.setUserType(updatedLog.getUserType());
                existing.setUserId(updatedLog.getUserId());
                existing.setMessage(updatedLog.getMessage());
                existing.setTimestamp(updatedLog.getTimestamp());
                return activityLogRepository.save(existing);
            })
            .orElse(null);
    }

    @Override
    public void deleteActivityLog(Long id) {
        activityLogRepository.deleteById(id);
    }
}
