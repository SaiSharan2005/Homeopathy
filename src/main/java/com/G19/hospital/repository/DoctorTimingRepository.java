package com.G19.hospital.repository;

import com.G19.hospital.model.DoctorRegister;
import com.G19.hospital.model.DoctorTiming;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorTimingRepository extends JpaRepository<DoctorTiming, Long> {
    List<DoctorTiming> findByDoctorId(DoctorRegister doctorId);
    List<DoctorTiming> findByDoctorIdAndInUse(DoctorRegister doctorId, boolean inUse);
}
