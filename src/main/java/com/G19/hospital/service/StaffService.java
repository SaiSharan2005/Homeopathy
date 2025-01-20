package com.G19.hospital.service;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.User;

import java.util.List;

public interface StaffService {
    User registerStaff(StaffDTO staffDTO);
    // User loginStaff(String phoneNumber, String password);
    List<User> getAllStaff();
    User getStaffById(Long id);
    // User updateStaff(Long id, User staff);
    Boolean deleteStaff(Long id);
    User updateStaff(Long id, StaffDTO staffDTO); // Ensure this matches your implementation
    User createMyProfile(StaffDTO staffDTO) throws Exception;
    User updateMyProfile(StaffDTO staffDTO) throws Exception;
    User updateProfileById(Long id, StaffDTO staffDTO);
    
}
