package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.DoctorTimingDTO;
import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorTiming;
import com.G19.hospital.repository.DoctorTimingRepository;
import com.G19.hospital.service.DoctorTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorTimingServiceImpl implements DoctorTimingService {

    @Autowired
    private DoctorTimingRepository doctorTimingRepository;

    @Override
    public DoctorTimingDTO createDoctorTiming(DoctorTimingDTO doctorTimingDTO) {
        DoctorTiming doctorTiming = new DoctorTiming();
        doctorTiming.setDoctorId(doctorTimingDTO.getDoctorId());
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
            doctorTiming.setDoctorId(dto.getDoctorId());
            doctorTiming.setStartTime(dto.getStartTime());
            doctorTiming.setEndTime(dto.getEndTime());
            doctorTiming.setInUse(dto.isInUse());
            return doctorTiming;
        }).collect(Collectors.toList());

        List<DoctorTiming> savedTimings = doctorTimingRepository.saveAll(doctorTimings);
        return savedTimings.stream().map(savedTiming -> 
            new DoctorTimingDTO(savedTiming.getSlotId(), savedTiming.getDoctorId(), savedTiming.getStartTime(), savedTiming.getEndTime(), savedTiming.isInUse())
        ).collect(Collectors.toList());
    }


    @Override
    public DoctorTimingDTO updateDoctorTiming(Long slotId, DoctorTimingDTO doctorTimingDTO) {
        DoctorTiming doctorTiming = doctorTimingRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));
        doctorTiming.setDoctorId(doctorTimingDTO.getDoctorId());
        doctorTiming.setStartTime(doctorTimingDTO.getStartTime());
        doctorTiming.setEndTime(doctorTimingDTO.getEndTime());
        doctorTiming.setInUse(doctorTimingDTO.isInUse());

        doctorTiming = doctorTimingRepository.save(doctorTiming);
        return doctorTimingDTO;
    }

    @Override
    public void deleteDoctorTiming(Long slotId) {
        doctorTimingRepository.deleteById(slotId);
    }

    @Override
    public DoctorTimingDTO getDoctorTiming(Long slotId) {
        DoctorTiming doctorTiming = doctorTimingRepository.findById(slotId).orElseThrow(() -> new RuntimeException("Slot not found"));
        return new DoctorTimingDTO(
            doctorTiming.getSlotId(),
            doctorTiming.getDoctorId(),
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
                    doctorTiming.getDoctorId(),
                    doctorTiming.getStartTime(),
                    doctorTiming.getEndTime(),
                    doctorTiming.isInUse()))
                .collect(Collectors.toList());
    }

    @Override
    public void setInUseToFalseForDoctor(DoctorRegister doctorData) {
        List<DoctorTiming> doctorTimings = doctorTimingRepository.findByDoctorId(doctorData);
        for (DoctorTiming doctorTiming : doctorTimings) {
            doctorTiming.setInUse(false);
            doctorTimingRepository.save(doctorTiming);
        }
    }

    @Override
    public List<DoctorTimingDTO> getDoctorTimingsByDoctorIdAndInUse(DoctorRegister doctorId) {
        return doctorTimingRepository.findByDoctorIdAndInUse(doctorId, true).stream()
                .map(doctorTiming -> new DoctorTimingDTO(
                    doctorTiming.getSlotId(),
                    doctorTiming.getDoctorId(),
                    doctorTiming.getStartTime(),
                    doctorTiming.getEndTime(),
                    doctorTiming.isInUse()))
                .collect(Collectors.toList());
    }
}
