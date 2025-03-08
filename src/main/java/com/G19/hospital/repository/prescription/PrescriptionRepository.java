package  com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // Add custom query methods if needed
}
