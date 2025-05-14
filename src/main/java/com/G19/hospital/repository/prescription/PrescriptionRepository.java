package  com.G19.hospital.repository.prescription;

import com.G19.hospital.model.prescription.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
 
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // Add custom query methods if needed
        Optional<Prescription> findByBookingAppointment_BookingId(Long bookingId);
    Optional<Prescription> findByBookingAppointment_Token(String token);
    List<Prescription> findByDoctor_Id(Long doctorId);
    List<Prescription> findByPatient_Id(Long patientId);
    List<Prescription> findByPatient_IdOrderByDateIssuedDesc(Long patientId);

}
