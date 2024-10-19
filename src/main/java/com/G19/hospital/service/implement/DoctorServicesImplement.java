package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.DoctorDetailsRepository;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.DoctorServices;
import com.G19.hospital.util.Constants.ApiMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class DoctorServicesImplement implements DoctorServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorDetailsRepository doctorDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerDoctor(DoctorRegisterDTO doctorRegisterDTO) throws Exception {
        // Check if the phone number already exists in the User repository
        if (userRepository.existsByUsername(doctorRegisterDTO.getPhoneNumber())) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        // Create a new User and set properties from the DoctorRegisterDTO
        User doctor = new User();
        doctor.setUsername(doctorRegisterDTO.getDoctorName()); // Treat phone number as username
        doctor.setEmail(doctorRegisterDTO.getEmail());
        doctor.setPassword(passwordEncoder.encode(doctorRegisterDTO.getPassword()));
        doctor.setPhoneNumber(doctorRegisterDTO.getPhoneNumber());
        // Assign the role of "DOCTOR"
        Set<Role> roles = new HashSet<>();
        Role doctorRole = roleRepository.findByName("DOCTOR");
        roles.add(doctorRole);
        doctor.setRoles(roles);

        String userId;
        Random random = new Random();
        do {
            String firstNamePart = doctorRegisterDTO.getDoctorName().substring(0,
                Math.min(doctorRegisterDTO.getDoctorName().length(), 4));
            String lastNamePart = doctorRegisterDTO.getPhoneNumber()
                    .substring(Math.max(doctorRegisterDTO.getPhoneNumber().length() - 4, 0));
            
            // Add a random number between 1000 and 9999 to ensure uniqueness
            int randomNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999
            userId = "D29" + firstNamePart  + randomNumber;
    
        } while (userRepository.existsByUserId(userId));
    
        doctor.setUserId(userId);// Save the doctor (User) to the repository
        return userRepository.save(doctor);
    }

    @Override
    public User loginDoctor(String phoneNumber, String password) throws Exception {
        // Find the doctor by phone number (username)
        User doctor = userRepository.findByUsername(phoneNumber)
                .orElseThrow(() -> new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST));

        // Verify the password
        if (!passwordEncoder.matches(password, doctor.getPassword())) {
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }

        return doctor;
    }

    @Override
    public DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws Exception {
        // Find the associated doctor (User)
        User doctor = userRepository.findById(doctorDetailsDTO.getDoctorId())
                .orElseThrow(() -> new CustomSecurityException("Doctor not found", HttpStatus.NOT_FOUND));

        // Create and populate the DoctorDetails entity
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setAge(doctorDetailsDTO.getAge());
        doctorDetails.setGender(doctorDetailsDTO.getGender());
        doctorDetails.setAddress(doctorDetailsDTO.getAddress());
        doctorDetails.setCity(doctorDetailsDTO.getCity());
        doctorDetails.setPincode(doctorDetailsDTO.getPincode());
        doctorDetails.setConsultationFee(doctorDetailsDTO.getConsultationFee());
        doctorDetails.setSpecialization(doctorDetailsDTO.getSpecialization());
        doctorDetails.setRemuneration(doctorDetailsDTO.getRemuneration());
        doctorDetails.setUser(doctor);

        // Save the doctor details
        return doctorDetailsRepository.save(doctorDetails);
    }

    @Override
    public User getDoctorByDoctorId(String doctorId) throws Exception {
        return userRepository.findByUserId(doctorId)
                .orElseThrow(() -> new CustomSecurityException("Doctor not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAllDoctors() throws Exception {
        Role doctorRole = roleRepository.findByName("DOCTOR");
        return userRepository.findByRoles(doctorRole);
    }

    @Override
    public List<User> searchDoctors(String keyword) throws Exception {
        // Search by username (phone number) or email for doctors
        Role doctorRole = roleRepository.findByName("Doctor");
        return userRepository.searchUsers(keyword, doctorRole);
    }

    @Override
    public long getDoctorCount() throws Exception {
        Role doctorRole = roleRepository.findByName("ROLE_DOCTOR");
        return userRepository.countByRoles(doctorRole);
    }
}
