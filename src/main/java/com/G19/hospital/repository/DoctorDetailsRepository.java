

package com.G19.hospital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.DoctorDetails;
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long>{
    
}
