package com.G19.hospital.service;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.Staff;

import java.util.List;

public interface StaffService {
    Staff registerStaff(StaffDTO staffDTO);
    Staff loginStaff(String phoneNumber, String password);
    List<Staff> getAllStaff();
    Staff getStaffById(Long id);
    Staff updateStaff(Long id, Staff staff);
    void deleteStaff(Long id);
}
