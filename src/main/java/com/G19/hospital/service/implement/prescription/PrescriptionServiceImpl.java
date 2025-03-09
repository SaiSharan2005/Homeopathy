package com.G19.hospital.service.impl.prescription;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.prescription.Prescription;
import com.G19.hospital.model.prescription.PrescriptionItem;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.repository.prescription.PrescriptionRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final InventoryItemRepository inventoryItemRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   UserRepository userRepository,
                                   InventoryItemRepository inventoryItemRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = convertToEntity(prescriptionDto);
        if (prescription.getDateIssued() == null) {
            prescription.setDateIssued(LocalDateTime.now());
        }
        Prescription saved = prescriptionRepository.save(prescription);
        return convertToDto(saved);
    }

    @Override
    public PrescriptionDto updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {
        Prescription existing = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        existing.setPrescriptionNumber(prescriptionDto.getPrescriptionNumber());
        existing.setDateIssued(prescriptionDto.getDateIssued());
        existing.setGeneralInstructions(prescriptionDto.getGeneralInstructions());
        Prescription updated = prescriptionRepository.save(existing);
        return convertToDto(updated);
    }

    @Override
    public void deletePrescription(Long prescriptionId) {
        Prescription existing = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        prescriptionRepository.delete(existing);
    }

    @Override
    public PrescriptionDto getPrescriptionById(Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        return convertToDto(prescription);
    }

    @Override
    public List<PrescriptionDto> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return prescriptions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDto addPrescriptionItem(Long prescriptionId, PrescriptionItemDto prescriptionItemDto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));

        PrescriptionItem newItem = new PrescriptionItem();
        newItem.setDosage(prescriptionItemDto.getDosage());
        newItem.setFrequency(prescriptionItemDto.getFrequency());
        newItem.setDuration(prescriptionItemDto.getDuration());
        newItem.setAdditionalInstructions(prescriptionItemDto.getAdditionalInstructions());
        
        // Retrieve the InventoryItem from the repository
        InventoryItem inventoryItem = inventoryItemRepository.findById(prescriptionItemDto.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
        newItem.setInventoryItem(inventoryItem);
        
        newItem.setPrescription(prescription);
        prescription.getPrescriptionItems().add(newItem);
        
        Prescription updated = prescriptionRepository.save(prescription);
        return convertToDto(updated);
    }

    // Helper method to convert Prescription entity to DTO
private PrescriptionDto convertToDto(Prescription prescription) {
    PrescriptionDto dto = new PrescriptionDto();
    dto.setId(prescription.getId());
    dto.setPrescriptionNumber(prescription.getPrescriptionNumber());
    dto.setDateIssued(prescription.getDateIssued());
    dto.setGeneralInstructions(prescription.getGeneralInstructions());
    if (prescription.getDoctor() != null) {
        dto.setDoctorId(prescription.getDoctor().getId());
    }
    if (prescription.getPatient() != null) {
        dto.setPatientId(prescription.getPatient().getId());
    }
    // Map prescription items if available
    if (prescription.getPrescriptionItems() != null) {
        dto.setPrescriptionItems(
            prescription.getPrescriptionItems().stream().map(item -> {
                PrescriptionItemDto itemDto = new PrescriptionItemDto();
                itemDto.setId(item.getId());
                itemDto.setInventoryItemId(item.getInventoryItem().getId());
                itemDto.setDosage(item.getDosage());
                itemDto.setFrequency(item.getFrequency());
                itemDto.setDuration(item.getDuration());
                itemDto.setAdditionalInstructions(item.getAdditionalInstructions());
                return itemDto;
            }).collect(Collectors.toList())
        );
    }
    return dto;
}

    // Helper method to convert DTO to Prescription entity
    private Prescription convertToEntity(PrescriptionDto dto) {
        Prescription prescription = new Prescription();
        prescription.setId(dto.getId());
        prescription.setPrescriptionNumber(dto.getPrescriptionNumber());
        prescription.setDateIssued(dto.getDateIssued());
        prescription.setGeneralInstructions(dto.getGeneralInstructions());
        if (dto.getDoctorId() != null) {
            User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new CustomSecurityException("Doctor not found", HttpStatus.NOT_FOUND));
            prescription.setDoctor(doctor);
        }
        if (dto.getPatientId() != null) {
            User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new CustomSecurityException("Patient not found", HttpStatus.NOT_FOUND));
            prescription.setPatient(patient);
        }
        // TODO: Map prescription items if necessary
        return prescription;
    }
}
