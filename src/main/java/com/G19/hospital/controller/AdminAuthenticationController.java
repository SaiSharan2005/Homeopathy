package com.G19.hospital.controller;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.User;
import com.G19.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthenticationController {

    @Autowired
    private AdminService adminService;

    // ---------------- Existing endpoints ----------------

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody StaffDTO dto) {
        User newUser = adminService.registerStaff(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/createMyProfile")
    public ResponseEntity<?> createMyProfile(@RequestBody StaffDTO dto) {
        try {
            User created = adminService.createMyProfile(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Profile creation failed: " + ex.getMessage());
        }
    }

    @PutMapping("/updateMyProfile")
    public ResponseEntity<?> updateMyProfile(@RequestBody StaffDTO dto) {
        try {
            User updated = adminService.updateMyProfile(dto);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Profile update failed: " + ex.getMessage());
        }
    }

    @PutMapping("/updateProfileById/{id}")
    public ResponseEntity<?> updateProfileById(
            @PathVariable Long id,
            @RequestBody StaffDTO dto) {
        try {
            User updated = adminService.updateProfileById(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllStaff());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.getStaffById(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = adminService.deleteStaff(id);
        return deleted
                ? ResponseEntity.ok("User deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // ---------------- Role-management endpoints ----------------

    @GetMapping("/{userId}/roles")
    public ResponseEntity<Set<String>> getUserRoles(@PathVariable Long userId) {
        try {
            Set<String> roles = adminService.getUserRoles(userId);
            return ResponseEntity.ok(roles);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/{userId}/roles")
    public ResponseEntity<String> updateUserRoles(
            @PathVariable Long userId,
            @RequestBody List<String> roleNames) {
        try {
            adminService.updateUserRoles(userId, roleNames);
            return ResponseEntity.ok("Roles updated successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @DeleteMapping("/{userId}/roles")
    public ResponseEntity<String> removeUserRole(
            @PathVariable Long userId,
            @RequestParam String roleName) {
        try {
            adminService.removeUserRole(userId, roleName);
            return ResponseEntity.ok("Role removed successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/role/{roleName}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        try {
            List<User> users = adminService.getUsersByRole(roleName);
            return ResponseEntity.ok(users);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/staff-roles")
    public ResponseEntity<List<User>> getAllStaffUsers() {
        try {
            List<User> staff = adminService.getUsersByRole("STAFF");
            if (staff.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(staff);
            }
            return ResponseEntity.ok(staff);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
