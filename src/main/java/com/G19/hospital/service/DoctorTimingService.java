package com.G19.hospital.service;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorTiming;

import java.util.List;

public interface DoctorTimingService {
    DoctorTimingDTO createDoctorTiming(DoctorTimingDTO doctorTimingDTO);
    List<DoctorTimingDTO> createDoctorTimings(List<DoctorTimingDTO> doctorTimingDTOs);
    DoctorTimingDTO updateDoctorTiming(Long slotId, DoctorTimingDTO doctorTimingDTO);
    void deleteDoctorTiming(Long slotId);
    DoctorTimingDTO getDoctorTiming(Long slotId);
    List<DoctorTimingDTO> getAllDoctorTimings();
    void setInUseToFalseForDoctor(DoctorRegister doctorId);
    List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(DoctorRegister doctorId);

}
