package com.G19.hospital.service.implement;

import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.repository.ActivityLogRepository;
import com.G19.hospital.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Override
    public ActivityLog createActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    @Override
    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    @Override
    public Optional<ActivityLog> getActivityLogById(Long id) {
        return activityLogRepository.findById(id);
    }

    @Override
    public ActivityLog updateActivityLog(Long id, ActivityLog updatedLog) {
        Optional<ActivityLog> optionalLog = activityLogRepository.findById(id);
        if (optionalLog.isPresent()) {
            ActivityLog existingLog = optionalLog.get();
            existingLog.setUserType(updatedLog.getUserType());
            existingLog.setUserId(updatedLog.getUserId());
            existingLog.setMessage(updatedLog.getMessage());
            existingLog.setTimestamp(updatedLog.getTimestamp());
            return activityLogRepository.save(existingLog);
        }
        return null;
    }

    @Override
    public void deleteActivityLog(Long id) {
        activityLogRepository.deleteById(id);
    }
}
