// src/main/java/com/G19/hospital/service/AdminService.java
package com.G19.hospital.service;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.User;

import java.util.List;
import java.util.Set;

public interface AdminService {
    User registerStaff(StaffDTO dto);
    User createMyProfile(StaffDTO dto) throws Exception;
    User updateMyProfile(StaffDTO dto) throws Exception;
    User updateProfileById(Long id, StaffDTO dto);
    List<User> getAllStaff();
    User getStaffById(Long id);
    Boolean deleteStaff(Long id);
    User updateStaff(Long id, StaffDTO dto);

    // Role‐management
    Set<String> getUserRoles(Long userId);
    void updateUserRoles(Long userId, List<String> roleNames);
    void removeUserRole(Long userId, String roleName);
    List<User> getUsersByRole(String roleName);
}
