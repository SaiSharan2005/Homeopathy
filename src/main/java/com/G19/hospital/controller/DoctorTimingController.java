package com.G19.hospital.controller;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.service.DoctorTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-timings")
public class DoctorTimingController {

    @Autowired
    private DoctorTimingService doctorTimingService;

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
    public void deleteDoctorTiming(@PathVariable Long slotId) {
        doctorTimingService.deleteDoctorTiming(slotId);
    }

    @GetMapping("/{slotId}")
    public DoctorTimingDTO getDoctorTiming(@PathVariable Long slotId) {
        return doctorTimingService.getDoctorTiming(slotId);
    }

    @GetMapping
    public List<DoctorTimingDTO> getAllDoctorTimings() {
        return doctorTimingService.getAllDoctorTimings();
    }

    @PostMapping("/set-in-use-false")
    public void setInUseToFalseForDoctor(@RequestBody DoctorRegister doctorId) {
        doctorTimingService.setInUseToFalseForDoctor(doctorId);
    }

    @GetMapping("/doctor/in-use")
    public List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(@RequestBody DoctorRegister doctorId) {
        return doctorTimingService.getDoctorTimingsByDoctorIdAndInUse(doctorId);
    }
}
