// src/main/java/com/G19/hospital/service/implement/AdminServiceImpl.java
package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public User registerStaff(StaffDTO dto) {
        User u = new User();
        u.setUsername(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role adminRole = roleRepo.findByName("ADMIN");
        u.setRoles(Collections.singleton(adminRole));

        // generate unique userId
        String base = dto.getName().substring(0, Math.min(4, dto.getName().length()));
        String userId;
        do {
            int rand = new Random().nextInt(9000) + 1000;
            userId = "A" + base + rand;
        } while (userRepo.existsByUserId(userId));
        u.setUserId(userId);

        return userRepo.save(u);
    }

    @Override
    public User createMyProfile(StaffDTO dto) throws Exception {
        return registerStaff(dto);
    }

    @Override
    public User updateMyProfile(StaffDTO dto) throws Exception {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userRepo.findByPhoneNumber(phone)
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        return updateProfileById(u.getId(), dto);
    }

    @Override
    public User updateProfileById(Long id, StaffDTO dto) {
        User u = getStaffById(id);
        u.setUsername(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getPassword() != null) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userRepo.save(u);
    }

    @Override
    public List<User> getAllStaff() {
        Role staffRole = roleRepo.findByName("ADMIN");
        return userRepo.findByRoles(staffRole);
    }

    @Override
    public User getStaffById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Override
    public Boolean deleteStaff(Long id) {
        return userRepo.deleteById(id);
    }

    @Override
    public User updateStaff(Long id, StaffDTO dto) {
        return updateProfileById(id, dto);
    }

    // ——— Role‐management logic ———

    @Override
    public Set<String> getUserRoles(Long userId) {
        User u = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return u.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void updateUserRoles(Long userId, List<String> roleNames) {
        User u = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> roles = new HashSet<>();
        for (String name : roleNames) {
            Role r = roleRepo.findByName(name);
            if (r == null) {
                throw new RuntimeException("Unknown role: " + name);
            }
            roles.add(r);
        }
        u.setRoles(roles);
        userRepo.save(u);
    }

    @Override
    public void removeUserRole(Long userId, String roleName) {
        User u = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Role r = roleRepo.findByName(roleName);
        if (r == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        if (!u.getRoles().remove(r)) {
            throw new RuntimeException("User did not have role: " + roleName);
        }
        userRepo.save(u);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        Role r = roleRepo.findByName(roleName);
        if (r == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        return userRepo.findByRoles(r);
    }
}
