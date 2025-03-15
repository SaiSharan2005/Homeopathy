package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.User; // Import User instead of DoctorRegister
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.DoctorTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorTimingServiceImpl implements DoctorTimingService {

    @Autowired
    private DoctorTimingRepository doctorTimingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DoctorTimingDTO createDoctorTiming(DoctorTimingDTO doctorTimingDTO) {
        DoctorTiming doctorTiming = new DoctorTiming();
        User doctor = userRepository.findById(doctorTimingDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorTiming.setDoctor(doctor); // Set the User object
        doctorTiming.setStartTime(doctorTimingDTO.getStartTime());
        doctorTiming.setEndTime(doctorTimingDTO.getEndTime());
        doctorTiming.setInUse(doctorTimingDTO.isInUse());

        doctorTiming = doctorTimingRepository.save(doctorTiming);
        doctorTimingDTO.setSlotId(doctorTiming.getSlotId());
        return doctorTimingDTO;
    }

    @Override
    public List<DoctorTimingDTO> createDoctorTimings(List<DoctorTimingDTO> doctorTimingDTOs) {
        List<DoctorTiming> doctorTimings = doctorTimingDTOs.stream().map(dto -> {
            DoctorTiming doctorTiming = new DoctorTiming();
            User doctor = userRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            doctorTiming.setDoctor(doctor); // Set the User object
            doctorTiming.setStartTime(dto.getStartTime());
            doctorTiming.setEndTime(dto.getEndTime());
            doctorTiming.setInUse(dto.isInUse());
            return doctorTiming;
        }).collect(Collectors.toList());

        List<DoctorTiming> savedTimings = doctorTimingRepository.saveAll(doctorTimings);
        return savedTimings.stream().map(savedTiming -> 
            new DoctorTimingDTO(savedTiming.getSlotId(), savedTiming.getDoctor().getId(), savedTiming.getStartTime(), savedTiming.getEndTime(), savedTiming.isInUse())
        ).collect(Collectors.toList());
    }

    @Override
    public DoctorTimingDTO updateDoctorTiming(Long slotId, DoctorTimingDTO doctorTimingDTO) {
        DoctorTiming doctorTiming = doctorTimingRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));
        User doctor = userRepository.findById(doctorTimingDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctorTiming.setDoctor(doctor); // Set the User object
        doctorTiming.setStartTime(doctorTimingDTO.getStartTime());
        doctorTiming.setEndTime(doctorTimingDTO.getEndTime());
        doctorTiming.setInUse(doctorTimingDTO.isInUse());

        doctorTiming = doctorTimingRepository.save(doctorTiming);
        return doctorTimingDTO;
    }

    public User getDoctorInfoByUserName(String username) {
        try {
            // Logic to retrieve the patient from the database using the phone number
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new Exception("Patient not found"));
        } catch (Exception e) {
            // Handle the exception (e.g., log it or throw a specific runtime exception)
            throw new RuntimeException("Error retrieving patient info: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteDoctorTiming(Long slotId) {
        if(doctorTimingRepository.findById(slotId).isPresent()){
            doctorTimingRepository.deleteById(slotId);
            return true;
        }

        return false;
    }

    @Override
    public DoctorTimingDTO getDoctorTiming(Long slotId) {
        DoctorTiming doctorTiming = doctorTimingRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));
        return new DoctorTimingDTO(
            doctorTiming.getSlotId(),
            doctorTiming.getDoctor().getId(), // Get the doctor's ID
            doctorTiming.getStartTime(),
            doctorTiming.getEndTime(),
            doctorTiming.isInUse()
        );
    }

    @Override
    public List<DoctorTimingDTO> getAllDoctorTimings() {
        return doctorTimingRepository.findAll().stream()
                .map(doctorTiming -> new DoctorTimingDTO(
                    doctorTiming.getSlotId(),
                    doctorTiming.getDoctor().getId(), // Get the doctor's ID
                    doctorTiming.getStartTime(),
                    doctorTiming.getEndTime(),
                    doctorTiming.isInUse()))
                .collect(Collectors.toList());
    }

    @Override
    public void setInUseToFalseForDoctor(User doctor) { // Accept User
        List<DoctorTiming> doctorTimings = doctorTimingRepository.findByDoctor(doctor); // Use User for the query
        for (DoctorTiming doctorTiming : doctorTimings) {
            doctorTiming.setInUse(false);
            doctorTimingRepository.save(doctorTiming);
        }
    }

    @Override
    public List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(User doctor) { // Accept User
        return doctorTimingRepository.findByDoctorAndInUse(doctor, true).stream() // Use User for the query
                .map(doctorTiming -> new DoctorTimingDTO(
                    doctorTiming.getSlotId(),
                    doctorTiming.getDoctor().getId(), // Get the doctor's ID
                    doctorTiming.getStartTime(),
                    doctorTiming.getEndTime(),
                    doctorTiming.isInUse()))
                .collect(Collectors.toList());
    }
}
