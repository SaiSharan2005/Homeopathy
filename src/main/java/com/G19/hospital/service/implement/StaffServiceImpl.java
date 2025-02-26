package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.StaffDTO;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerStaff(StaffDTO staffDTO) {
        User user = new User();
        user.setUsername(staffDTO.getName()); // Assuming staff name is used as username
        user.setEmail(staffDTO.getEmail());
        user.setPhoneNumber(staffDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(staffDTO.getPassword())); // Encode password

        Set<Role> roles = new HashSet<>();
        Role doctorRole = roleRepository.findByName("ADMIN");
        roles.add(doctorRole);
        user.setRoles(roles);

        String userId;
        Random random = new Random();
        do {
            String firstNamePart = staffDTO.getName().substring(0,
                    Math.min(staffDTO.getName().length(), 4));
            // String lastNamePart = staffDTO.getPhoneNumber()
            //         .substring(Math.max(staffDTO.getPhoneNumber().length() - 4, 0));

            int randomNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999
            userId = "A" + firstNamePart + randomNumber;

        } while (userRepository.existsByUserId(userId));

        user.setUserId(userId);// Save the doctor (User) to the repository

        user = userRepository.save(user);
        return user;
    }

    // @Override
    // public User loginStaff(String phoneNumber, String password) {
    // User user = userRepository.findByPhoneNumber(phoneNumber);
    // if (user != null && passwordEncoder.matches(password, user.getPassword())) {
    // return user;
    // } else {
    // throw new RuntimeException("Invalid phone number or password");
    // }
    // }

    @Override
    public List<User> getAllStaff() {
        // Assuming staff are identified by role
        Role staffRole = roleRepository.findByName("ADMIN");

        return userRepository.findByRoles(staffRole);
    }

    @Override
    public User getStaffById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Override
    public User updateStaff(Long id, StaffDTO staffDTO) {
        User existingStaff = getStaffById(id);
        existingStaff.setUsername(staffDTO.getName());
        existingStaff.setEmail(staffDTO.getEmail());
        existingStaff.setPhoneNumber(staffDTO.getPhoneNumber());
        existingStaff.setPassword(passwordEncoder.encode(staffDTO.getPassword())); // Update with encoded password
        return userRepository.save(existingStaff);
    }

    @Override
    public Boolean deleteStaff(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public User createMyProfile(StaffDTO staffDTO) throws Exception {
        // String username = getAuthenticatedUsername();

        // Ensure the authenticated user exists
        // User user = userRepository.findByPhoneNumber(username)
        //         .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Set the authenticated user's userId
        // staffDTO.setUserId(user.getUserId());

        // Create and save the profile
        return registerStaff(staffDTO);
    }

    @Override
    public User updateMyProfile(StaffDTO staffDTO) throws Exception {
        String username = getAuthenticatedUsername();

        // Ensure the authenticated user exists
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Set the authenticated user's userId in the DTO
        // staffDTO.setUserId(user.getUserId());

        // Update the profile
        return updateProfileById(user.getId(), staffDTO);
    }

    @Override
    public User updateProfileById(Long id, StaffDTO staffDTO) {
        User existingStaff = getStaffById(id);

        // Update fields
        existingStaff.setUsername(staffDTO.getName());
        existingStaff.setEmail(staffDTO.getEmail());
        existingStaff.setPhoneNumber(staffDTO.getPhoneNumber());

        if (staffDTO.getPassword() != null) {
            existingStaff.setPassword(passwordEncoder.encode(staffDTO.getPassword()));
        }

        // Save updated staff user
        return userRepository.save(existingStaff);
    }

    // Utility method to get authenticated user's username
    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
