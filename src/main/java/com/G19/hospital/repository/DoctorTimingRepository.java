package com.G19.hospital.repository;

import com.G19.hospital.model.User; // Import User instead of DoctorRegister
import com.G19.hospital.model.DoctorTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorTimingRepository extends JpaRepository<DoctorTiming, Long> {
    // Change the parameter type from DoctorRegister to User
    List<DoctorTiming> findByDoctor(User doctor); // Use the reference type

    // Change the parameter type from DoctorRegister to User
    List<DoctorTiming> findByDoctorAndInUse(User doctor, boolean inUse); // Use the reference type
}
