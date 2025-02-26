package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.PrescriptionDto;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.Prescription;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.PrescriptionRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.PrescriptionService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final BookingAppointmentRepository bookingAppointmentRepository;
    private final Cloudinary cloudinary;

    // @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   UserRepository userRepository,
                                   BookingAppointmentRepository bookingAppointmentRepository,
                                   Cloudinary cloudinary) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
        this.bookingAppointmentRepository = bookingAppointmentRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public Prescription createPrescription(PrescriptionDto prescriptionDto, MultipartFile file) {
        Prescription prescription = new Prescription();
        // Retrieve patient, doctor, and appointment from their repositories
        User patient = userRepository.findById(prescriptionDto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor = userRepository.findById(prescriptionDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        BookingAppointment appointment = bookingAppointmentRepository.findById(prescriptionDto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Upload the file to Cloudinary and retrieve the URL
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();
            prescription.setImageUrl(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setAppointment(appointment);
        prescription.setDescription(prescriptionDto.getDescription());

        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return prescriptionRepository.findByPatient(patient);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return prescriptionRepository.findByDoctor(doctor);
    }

    @Override
    public Prescription updatePrescription(Long id, PrescriptionDto prescriptionDto) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        // Update allowed fields (for instance description)
        if (prescriptionDto.getDescription() != null) {
            prescription.setDescription(prescriptionDto.getDescription());
        }
        // Note: To update the image, you could add an overloaded method that accepts a new MultipartFile.
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deletePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        prescriptionRepository.delete(prescription);
    }
}
