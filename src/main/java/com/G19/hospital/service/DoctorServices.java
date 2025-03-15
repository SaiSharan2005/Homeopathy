package com.G19.hospital.service;

import com.G19.hospital.DTO.DoctorDetailsDTO;
import com.G19.hospital.DTO.DoctorRegisterDTO;
import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.User;

import java.util.*;

public interface DoctorServices {
    
    // Register a doctor and save to User repository
    User registerDoctor(DoctorRegisterDTO doctorRegisterDTO) throws Exception;

    // Doctor login based on phone number and password in the User repository
    User loginDoctor(String phoneNumber, String password) throws Exception;

    // Update doctor's profile in DoctorDetails
    DoctorDetails profileDoctor(DoctorDetailsDTO doctorDetailsDTO) throws Exception;

    // Fetch a doctor using doctorId (stored in the User model)
    User getDoctorByDoctorId(Long id ) throws Exception;

    User getDoctorInfoByUserName(String username);

    List<User> getAllAvailableDoctors() throws Exception;

    
    // Fetch all doctors (based on a role or attribute in User model)
    List<User> getAllDoctors() throws Exception;

    // Search doctors using a keyword (can be based on name, specialization, etc.)
    List<User> searchDoctors(String keyword) throws Exception;

    // Get total count of doctors
    long getDoctorCount() throws Exception;
    DoctorDetails updateDoctorProfile(DoctorDetailsDTO doctorDetailsDTO) throws Exception;

}
