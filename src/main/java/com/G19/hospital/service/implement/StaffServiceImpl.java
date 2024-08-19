package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.Staff;
import com.G19.hospital.repository.StaffRepository;
import com.G19.hospital.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff registerStaff(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setName(staffDTO.getName());
        staff.setRole(staffDTO.getRole());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setEmail(staffDTO.getEmail());
        staff.setPassword(staffDTO.getPassword());
        // Here you would hash the password before storing it
        // For simplicity, we are storing it as plain text
        // DO NOT DO THIS IN PRODUCTION
        staffRepository.save(staff);
        return staff;
    }

    @Override
    public Staff loginStaff(String phoneNumber, String password) {
        Staff staff = staffRepository.findByPhoneNumber(phoneNumber);
        if (staff != null && staff.getPassword().equals(password)) {
            return staff;
        } else {
            throw new RuntimeException("Invalid phone number or password");
        }
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Staff existingStaff = getStaffById(id);
        existingStaff.setName(staff.getName());
        existingStaff.setRole(staff.getRole());
        existingStaff.setPhoneNumber(staff.getPhoneNumber());
        existingStaff.setEmail(staff.getEmail());
        return staffRepository.save(existingStaff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
