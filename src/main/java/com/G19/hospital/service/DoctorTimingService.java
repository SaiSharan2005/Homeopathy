package com.G19.hospital.service;

import com.G19.hospital.DTO.ApiResponseDTO;
import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.User;

import java.util.List;

public interface DoctorTimingService {
    DoctorTimingDTO createDoctorTiming(DoctorTimingDTO doctorTimingDTO);
    ApiResponseDTO createDoctorTimings(List<DoctorTimingDTO> doctorTimingDTOs);
    DoctorTimingDTO updateDoctorTiming(Long slotId, DoctorTimingDTO doctorTimingDTO);
    boolean deleteDoctorTiming(Long slotId);
    DoctorTimingDTO getDoctorTiming(Long slotId);
    List<DoctorTimingDTO> getAllDoctorTimings();
    
    // Change parameter type from Long to User
    ApiResponseDTO setInUseToFalseForDoctor(User doctor); // Accept User instead of Long
    
    // Change parameter type from Long to User
    List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(User doctor); // Accept User instead of Long
}
