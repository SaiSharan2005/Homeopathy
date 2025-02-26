package com.G19.hospital.controller;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.User;
import com.G19.hospital.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class StaffAuthenticationController {

    @Autowired
    private StaffService staffService;

    // Register a new staff user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody StaffDTO staffDTO) {
        User newUser = staffService.registerStaff(staffDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Create staff profile for authenticated user
    @PostMapping("/createMyProfile")
    public ResponseEntity<?> createMyProfile(@RequestBody StaffDTO staffDTO) {
        try {
            User createdProfile = staffService.createMyProfile(staffDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile creation failed: " + e.getMessage());
        }
    }

    // Update profile for authenticated user
    @PutMapping("/updateMyProfile")
    public ResponseEntity<?> updateMyProfile(@RequestBody StaffDTO staffDTO) {
        try {
            User updatedProfile = staffService.updateMyProfile(staffDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile update failed: " + e.getMessage());
        }
    }

    // Update staff profile by ID
    @PutMapping("/updateProfileById/{id}")
    public ResponseEntity<?> updateProfileById(@PathVariable Long id, @RequestBody StaffDTO staffDTO) {
        try {
            User updatedProfile = staffService.updateProfileById(id, staffDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }
    }

    // Get all staff users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = staffService.getAllStaff();
        return ResponseEntity.ok(userList);
    }

    // Get specific staff user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = staffService.getStaffById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }
    }

    // Delete specific staff user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = staffService.deleteStaff(id);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
