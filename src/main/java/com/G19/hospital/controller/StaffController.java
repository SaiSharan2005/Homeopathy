package com.G19.hospital.controller;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.Staff;
import com.G19.hospital.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/register")
    public ResponseEntity<Staff> registerStaff(@RequestBody StaffDTO staffDTO) {
        Staff newStaff = staffService.registerStaff(staffDTO);
        return ResponseEntity.ok(newStaff);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginStaff(@RequestBody StaffDTO staffDTO) {
        try {
            Staff staff = staffService.loginStaff(staffDTO.getPhoneNumber(), staffDTO.getPassword());
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Staff updatedStaff = staffService.updateStaff(id, staff);
        return ResponseEntity.ok(updatedStaff);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted successfully");
    }
}
