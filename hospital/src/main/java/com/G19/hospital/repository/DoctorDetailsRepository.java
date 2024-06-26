

package com.G19.hospital.repository;
import com.G19.hospital.model.Authentication.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long>{
    
}
