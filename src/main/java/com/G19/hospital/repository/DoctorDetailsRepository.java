

package com.G19.hospital.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.DoctorDetails;
import com.G19.hospital.model.User;
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long>{

    Optional<DoctorDetails> findByUser(User doctor);
    
}
