package com.G19.hospital.service;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.User; // Import User instead of DoctorRegister
import java.util.List;

public interface DoctorTimingService {
    DoctorTimingDTO createDoctorTiming(DoctorTimingDTO doctorTimingDTO);
    List<DoctorTimingDTO> createDoctorTimings(List<DoctorTimingDTO> doctorTimingDTOs);
    DoctorTimingDTO updateDoctorTiming(Long slotId, DoctorTimingDTO doctorTimingDTO);
    boolean deleteDoctorTiming(Long slotId);
    DoctorTimingDTO getDoctorTiming(Long slotId);
    List<DoctorTimingDTO> getAllDoctorTimings();
    
    // Change parameter type from Long to User
    void setInUseToFalseForDoctor(User doctor); // Accept User instead of Long
    
    // Change parameter type from Long to User
    List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(User doctor); // Accept User instead of Long
}
