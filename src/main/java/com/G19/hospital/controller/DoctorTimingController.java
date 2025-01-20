package com.G19.hospital.controller;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.User;
import com.G19.hospital.service.DoctorTimingService;
import com.G19.hospital.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return doctorTimingService.createDoctorTiming(doctorTimingDTO);
    }

    @PostMapping("/multi")
    public List<DoctorTimingDTO> createDoctorTimings(@RequestBody List<DoctorTimingDTO> doctorTimingDTOs) {
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

    @PostMapping("/set-in-use-false/{doctorId}")
    public void setInUseToFalseForDoctor(@PathVariable Long doctorId) {  
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found")); // Fetch User object
        doctorTimingService.setInUseToFalseForDoctor(doctor); // Pass User object to service
    }

    @GetMapping("/doctor/{doctorId}/in-use")
    public List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(@PathVariable Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found")); // Fetch User object
        return doctorTimingService.getDoctorTimingsByDoctorIdAndInUse(doctor); // Pass User object to service
    }
}
