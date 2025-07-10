// PrescriptionServiceImpl.java
package com.G19.hospital.service.implement.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreatePrescriptionDto;
import com.G19.hospital.DTO.prescription.InstructionDto;
import com.G19.hospital.DTO.inventory.Prescription.*;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.prescription.Prescription;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.Prescription.PrescriptionRepository;
import com.G19.hospital.service.inventory.Prescription.PrescriptionService;
import com.G19.hospital.repository.BookingAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repo;
    private final UserRepository userRepo;
    private final BookingAppointmentRepository bookingRepo;

    @Override
    public PrescriptionDto     create(CreatePrescriptionDto dto) {
        User pat = userRepo.findById(dto.getPatientId())
          .orElseThrow(() -> new CustomSecurityException("Patient not found", HttpStatus.NOT_FOUND));
        User doc = userRepo.findById(dto.getDoctorId())
          .orElseThrow(() -> new CustomSecurityException("Doctor not found", HttpStatus.NOT_FOUND));
        Prescription p = new Prescription();
        p.setPatient(pat);
        p.setPrescribedBy(doc);
        if (dto.getBookingAppointmentId()!=null) {
          BookingAppointment b = bookingRepo.findById(dto.getBookingAppointmentId())
            .orElseThrow(() -> new CustomSecurityException("Booking not found", HttpStatus.NOT_FOUND));
          p.setBookingAppointment(b);
        }
        p.setRxDate(dto.getRxDate()!=null?dto.getRxDate():LocalDateTime.now());
        p.setNotes(dto.getNotes());
        repo.save(p);
        return toDto(p);
    }

    @Override
    public PrescriptionDto update(Long id, CreatePrescriptionDto dto) {
        Prescription p = repo.findById(id)
          .orElseThrow(() -> new CustomSecurityException("Not found", HttpStatus.NOT_FOUND));
        if (dto.getNotes()!=null) p.setNotes(dto.getNotes());
        if (dto.getRxDate()!=null) p.setRxDate(dto.getRxDate());
        repo.save(p);
        return toDto(p);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id))
          throw new CustomSecurityException("Not found", HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public PrescriptionDto getById(Long id) {
        return repo.findById(id).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<PrescriptionDto> getAll(Pageable pg) {
        return repo.findAll(pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public PrescriptionDto getByBookingId(Long bookingId) {
        return repo.findByBookingAppointment_BookingId(bookingId).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public PrescriptionDto getByToken(String token) {
        return repo.findByBookingAppointment_Token(token).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<PrescriptionDto> getByDoctor(Long docId, Pageable pg) {
        return repo.findByPrescribedBy_Id(docId, pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public Page<PrescriptionDto> getByPatient(Long patId, Pageable pg) {
        return repo.findByPatient_Id(patId, pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public List<InstructionDto> getInstructionsByPatient(Long patId) {
        // reuse your existing InstructionDto mapping
        return repo.findByPatient_IdOrderByRxDateDesc(patId, Pageable.unpaged())
          .stream()
          .map(p -> new InstructionDto(p.getRxDate(), p.getNotes()))
          .collect(Collectors.toList());
    }

    private PrescriptionDto toDto(Prescription p) {
        Set<PrescriptionItemDto> items = p.getItems().stream()
          .map(i -> new PrescriptionItemDto(
             i.getId(),
             p.getId(),
             i.getDrug().getId(),
             i.getFrequency(),
             i.getDuration(),
             i.getQuantity(),
             i.getAdditionalInstructions()))
          .collect(Collectors.toSet());
        return new PrescriptionDto(
          p.getId(),
          p.getPatient().getId(),
          p.getPrescribedBy().getId(),
          p.getBookingAppointment()!=null? p.getBookingAppointment().getBookingId():null,
          p.getRxDate(),
          p.getNotes(),
          items);
    }
}
