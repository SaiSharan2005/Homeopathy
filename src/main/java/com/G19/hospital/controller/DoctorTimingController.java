package com.G19.hospital.controller;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.User;
import com.G19.hospital.service.DoctorTimingService;
import com.G19.hospital.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-timings")
public class DoctorTimingController {

    @Autowired
    private DoctorTimingService doctorTimingService;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

  
    @PostMapping
    public DoctorTimingDTO createDoctorTiming(@RequestBody DoctorTimingDTO doctorTimingDTO) {
        // Extract the authenticated user's details from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the doctor's identifier

        // Fetch the doctor (User) entity based on the username
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Set the doctor ID in the DTO
        doctorTimingDTO.setDoctorId(doctor.getId());

        return doctorTimingService.createDoctorTiming(doctorTimingDTO);
    }

    @PostMapping("/multi")
    public List<DoctorTimingDTO> createDoctorTimings(@RequestBody List<DoctorTimingDTO> doctorTimingDTOs) {
        // Extract the authenticated user's details from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the doctor's identifier

        // Fetch the doctor (User) entity based on the username
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Set the doctor ID in each DTO
        doctorTimingDTOs.forEach(dto -> dto.setDoctorId(doctor.getId()));

        return doctorTimingService.createDoctorTimings(doctorTimingDTOs);
    }
    @PutMapping("/{slotId}")
    public DoctorTimingDTO updateDoctorTiming(@PathVariable Long slotId, @RequestBody DoctorTimingDTO doctorTimingDTO) {
        return doctorTimingService.updateDoctorTiming(slotId, doctorTimingDTO);
    }

    @DeleteMapping("/{slotId}")
    public boolean deleteDoctorTiming(@PathVariable Long slotId) {
        return doctorTimingService.deleteDoctorTiming(slotId);
    }

    @GetMapping("/{slotId}")
    public DoctorTimingDTO getDoctorTiming(@PathVariable Long slotId) {
        return doctorTimingService.getDoctorTiming(slotId);
    }

    @GetMapping("/all")
    public List<DoctorTimingDTO> getAllDoctorTimings() {
        return doctorTimingService.getAllDoctorTimings();
    }

    @PostMapping("/set-in-use-false")
    public void setInUseToFalseForDoctor() {  
        // Extract the authenticated user's details from the token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the doctor's identifier
    
        // Fetch the doctor (User) entity based on the username
        User doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    
        // Pass the User object to the service
        doctorTimingService.setInUseToFalseForDoctor(doctor);
    }
    

    @GetMapping("/doctor/{doctorId}/in-use")
    public List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(@PathVariable Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found")); // Fetch User object
        return doctorTimingService.getDoctorTimingsByDoctorIdAndInUse(doctor); // Pass User object to service
    }
}
