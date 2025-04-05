// package com.G19.hospital.service.impl.prescription;

// import com.G19.hospital.DTO.prescription.PrescriptionDto;
// import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
// import com.G19.hospital.exceptions.security.CustomSecurityException;
// import com.G19.hospital.model.prescription.Prescription;
// import com.G19.hospital.model.prescription.PrescriptionItem;
// import com.G19.hospital.model.User;
// import com.G19.hospital.model.inventory.InventoryItem;
// import com.G19.hospital.model.BookingAppointment;  // Make sure this is imported
// import com.G19.hospital.repository.prescription.PrescriptionRepository;
// import com.G19.hospital.repository.UserRepository;
// import com.G19.hospital.repository.inventory.InventoryItemRepository;
// import com.G19.hospital.repository.BookingAppointmentRepository; // NEW: repository for booking appointments
// import com.G19.hospital.service.PrescriptionService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class PrescriptionServiceImpl implements PrescriptionService {

//     private final PrescriptionRepository prescriptionRepository;
//     private final UserRepository userRepository;
//     private final InventoryItemRepository inventoryItemRepository;
//     private final BookingAppointmentRepository bookingAppointmentRepository; // NEW

//     @Autowired
//     public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
//                                    UserRepository userRepository,
//                                    InventoryItemRepository inventoryItemRepository,
//                                    BookingAppointmentRepository bookingAppointmentRepository) { // NEW
//         this.prescriptionRepository = prescriptionRepository;
//         this.userRepository = userRepository;
//         this.inventoryItemRepository = inventoryItemRepository;
//         this.bookingAppointmentRepository = bookingAppointmentRepository; // NEW
//     }

//     @Override
//     public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
//         Prescription prescription = convertToEntity(prescriptionDto);
//         if (prescription.getDateIssued() == null) {
//             prescription.setDateIssued(LocalDateTime.now());
//         }
//         Prescription saved = prescriptionRepository.save(prescription);
//         return convertToDto(saved);
//     }

//     @Override
//     public PrescriptionDto updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {
//         Prescription existing = prescriptionRepository.findById(prescriptionId)
//                 .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
//         existing.setPrescriptionNumber(prescriptionDto.getPrescriptionNumber());
//         existing.setDateIssued(prescriptionDto.getDateIssued());
//         existing.setGeneralInstructions(prescriptionDto.getGeneralInstructions());
//         // Optionally update booking appointment if needed:
//         if (prescriptionDto.getBookingAppointmentId() != null) {
//             BookingAppointment booking = bookingAppointmentRepository.findById(prescriptionDto.getBookingAppointmentId())
//                 .orElseThrow(() -> new CustomSecurityException("Booking appointment not found", HttpStatus.NOT_FOUND));
//             existing.setBookingAppointment(booking);
//         }
//         Prescription updated = prescriptionRepository.save(existing);
//         return convertToDto(updated);
//     }

//     @Override
//     public void deletePrescription(Long prescriptionId) {
//         Prescription existing = prescriptionRepository.findById(prescriptionId)
//                 .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
//         prescriptionRepository.delete(existing);
//     }

//     @Override
//     public PrescriptionDto getPrescriptionById(Long prescriptionId) {
//         Prescription prescription = prescriptionRepository.findById(prescriptionId)
//                 .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
//         return convertToDto(prescription);
//     }

//     @Override
//     public List<PrescriptionDto> getAllPrescriptions() {
//         List<Prescription> prescriptions = prescriptionRepository.findAll();
//         return prescriptions.stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }

//     @Override
//     public PrescriptionDto addPrescriptionItem(Long prescriptionId, PrescriptionItemDto prescriptionItemDto) {
//         Prescription prescription = prescriptionRepository.findById(prescriptionId)
//                 .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));

//         PrescriptionItem newItem = new PrescriptionItem();
//         newItem.setDosage(prescriptionItemDto.getDosage());
//         newItem.setFrequency(prescriptionItemDto.getFrequency());
//         newItem.setDuration(prescriptionItemDto.getDuration());
//         newItem.setAdditionalInstructions(prescriptionItemDto.getAdditionalInstructions());
        
//         // Retrieve the InventoryItem from the repository
//         InventoryItem inventoryItem = inventoryItemRepository.findById(prescriptionItemDto.getInventoryItemId())
//                 .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
//         newItem.setInventoryItem(inventoryItem);
        
//         newItem.setPrescription(prescription);
//         prescription.getPrescriptionItems().add(newItem);
        
//         Prescription updated = prescriptionRepository.save(prescription);
//         return convertToDto(updated);
//     }

//     @Override
//     public PrescriptionDto getPrescriptionByBookingId(Long bookingId) {
//         Prescription prescription = prescriptionRepository.findByBookingAppointment_BookingId(bookingId)
//             .orElseThrow(() -> new CustomSecurityException("Prescription not found for booking id " + bookingId, HttpStatus.NOT_FOUND));
//         return convertToDto(prescription);
//     }

//     @Override
//     public PrescriptionDto getPrescriptionByToken(String token) {
//         Prescription prescription = prescriptionRepository.findByBookingAppointment_Token(token)
//             .orElseThrow(() -> new CustomSecurityException("Prescription not found for token " + token, HttpStatus.NOT_FOUND));
//         return convertToDto(prescription);
//     }

//     @Override
//     public List<PrescriptionDto> getPrescriptionsByDoctor(Long doctorId) {
//         List<Prescription> prescriptions = prescriptionRepository.findByDoctor_Id(doctorId);
//         return prescriptions.stream().map(this::convertToDto).collect(Collectors.toList());
//     }

//     @Override
//     public List<PrescriptionDto> getPrescriptionsByPatient(Long patientId) {
//         List<Prescription> prescriptions = prescriptionRepository.findByPatient_Id(patientId);
//         return prescriptions.stream().map(this::convertToDto).collect(Collectors.toList());
//     }

//     // Helper method to convert Prescription entity to DTO
//     private PrescriptionDto convertToDto(Prescription prescription) {
//         PrescriptionDto dto = new PrescriptionDto();
//         dto.setId(prescription.getId());
//         dto.setPrescriptionNumber(prescription.getPrescriptionNumber());
//         dto.setDateIssued(prescription.getDateIssued());
//         dto.setGeneralInstructions(prescription.getGeneralInstructions());
//         if (prescription.getDoctor() != null) {
//             dto.setDoctorId(prescription.getDoctor().getId());
//         }
//         if (prescription.getPatient() != null) {
//             dto.setPatientId(prescription.getPatient().getId());
//         }
//         if (prescription.getBookingAppointment() != null) {
//             dto.setBookingAppointmentId(prescription.getBookingAppointment().getBookingId());
//         }
//         // Map prescription items if available
//         if (prescription.getPrescriptionItems() != null) {
//             dto.setPrescriptionItems(
//                 prescription.getPrescriptionItems().stream().map(item -> {
//                     PrescriptionItemDto itemDto = new PrescriptionItemDto();
//                     itemDto.setId(item.getId());
//                     itemDto.setInventoryItemId(item.getInventoryItem().getId());
//                     itemDto.setDosage(item.getDosage());
//                     itemDto.setFrequency(item.getFrequency());
//                     itemDto.setDuration(item.getDuration());
//                     itemDto.setAdditionalInstructions(item.getAdditionalInstructions());
//                     return itemDto;
//                 }).collect(Collectors.toList())
//             );
//         }
//         return dto;
//     }

//     // Helper method to convert DTO to Prescription entity
//     private Prescription convertToEntity(PrescriptionDto dto) {
//         Prescription prescription = new Prescription();
//         prescription.setId(dto.getId());
//         prescription.setPrescriptionNumber(dto.getPrescriptionNumber());
//         prescription.setDateIssued(dto.getDateIssued());
//         prescription.setGeneralInstructions(dto.getGeneralInstructions());
//         if (dto.getDoctorId() != null) {
//             User doctor = userRepository.findById(dto.getDoctorId())
//                 .orElseThrow(() -> new CustomSecurityException("Doctor not found", HttpStatus.NOT_FOUND));
//             prescription.setDoctor(doctor);
//         }
//         if (dto.getPatientId() != null) {
//             User patient = userRepository.findById(dto.getPatientId())
//                 .orElseThrow(() -> new CustomSecurityException("Patient not found", HttpStatus.NOT_FOUND));
//             prescription.setPatient(patient);
//         }
//         if (dto.getBookingAppointmentId() != null) {
//             // Retrieve the BookingAppointment and set it on the prescription
//             prescription.setBookingAppointment(
//                 bookingAppointmentRepository.findById(dto.getBookingAppointmentId())
//                 .orElseThrow(() -> new CustomSecurityException("Booking appointment not found", HttpStatus.NOT_FOUND))
//             );
//         }
//         // TODO: Map prescription items if necessary (usually they are added separately)
//         return prescription;
//     }
// }
package com.G19.hospital.service.implement.prescription;

import com.G19.hospital.DTO.prescription.PrescriptionDto;
import com.G19.hospital.DTO.prescription.PrescriptionItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.prescription.Prescription;
import com.G19.hospital.model.prescription.PrescriptionItem;
import com.G19.hospital.model.prescription.Payment;
import com.G19.hospital.model.prescription.PaymentMethod;
import com.G19.hospital.model.prescription.PaymentStatus;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.InventoryItem;
import com.G19.hospital.model.BookingAppointment;
import com.G19.hospital.repository.prescription.PrescriptionRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.InventoryItemRepository;
import com.G19.hospital.repository.BookingAppointmentRepository;
import com.G19.hospital.repository.prescription.PaymentRepository;  // NEW: Payment repository
import com.G19.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final BookingAppointmentRepository bookingAppointmentRepository;
    private final PaymentRepository paymentRepository; // NEW

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   UserRepository userRepository,
                                   InventoryItemRepository inventoryItemRepository,
                                   BookingAppointmentRepository bookingAppointmentRepository,
                                   PaymentRepository paymentRepository) { // NEW
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.bookingAppointmentRepository = bookingAppointmentRepository;
        this.paymentRepository = paymentRepository; // NEW
    }

    @Override
    public Prescription createPrescription(PrescriptionDto prescriptionDto) {
        Prescription prescription = convertToEntity(prescriptionDto);
        if (prescription.getDateIssued() == null) {
            prescription.setDateIssued(LocalDateTime.now());
        }
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription updatePrescription(Long prescriptionId, PrescriptionDto prescriptionDto) {
        Prescription existing = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        existing.setPrescriptionNumber(prescriptionDto.getPrescriptionNumber());
        existing.setDateIssued(prescriptionDto.getDateIssued());
        existing.setGeneralInstructions(prescriptionDto.getGeneralInstructions());
        if (prescriptionDto.getBookingAppointmentId() != null) {
            BookingAppointment booking = bookingAppointmentRepository.findById(prescriptionDto.getBookingAppointmentId())
                .orElseThrow(() -> new CustomSecurityException("Booking appointment not found", HttpStatus.NOT_FOUND));
            existing.setBookingAppointment(booking);
        }
        return prescriptionRepository.save(existing);
    }

    @Override
    public void deletePrescription(Long prescriptionId) {
        Prescription existing = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
        prescriptionRepository.delete(existing);
    }

    @Override
    public Prescription getPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Prescription addPrescriptionItem(Long prescriptionId, PrescriptionItemDto prescriptionItemDto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new CustomSecurityException("Prescription not found", HttpStatus.NOT_FOUND));

        // Create and populate the new prescription item
        PrescriptionItem newItem = new PrescriptionItem();
        newItem.setDosage(prescriptionItemDto.getDosage());
        newItem.setFrequency(prescriptionItemDto.getFrequency());
        newItem.setDuration(prescriptionItemDto.getDuration());
        newItem.setAdditionalInstructions(prescriptionItemDto.getAdditionalInstructions());
        newItem.setQuantity(prescriptionItemDto.getQuantity());

        // Retrieve and validate the InventoryItem
        if (prescriptionItemDto.getInventoryItemId() == null) {
            throw new CustomSecurityException("Inventory item ID is required", HttpStatus.BAD_REQUEST);
        }
        InventoryItem inventoryItem = inventoryItemRepository.findById(prescriptionItemDto.getInventoryItemId())
                .orElseThrow(() -> new CustomSecurityException("Inventory item not found", HttpStatus.NOT_FOUND));
        newItem.setInventoryItem(inventoryItem);
        newItem.setPrescription(prescription);
        prescription.getPrescriptionItems().add(newItem);

        // Save the updated prescription (which now includes the new item)
        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        // Calculate the new total amount from all prescription items.
        // For each item, compute cost = (sellingPrice * quantity)
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PrescriptionItem item : updatedPrescription.getPrescriptionItems()) {
            BigDecimal qty = new BigDecimal(item.getQuantity()); // Assumes quantity is a valid number string
            double sellingPrice = item.getInventoryItem().getSellingPrice();
            BigDecimal price = BigDecimal.valueOf(sellingPrice);
                        totalAmount = totalAmount.add(price.multiply(qty));
        }

        // Check if a Payment record exists for this prescription.
        Payment payment = paymentRepository.findByPrescriptionId(updatedPrescription.getId());
        if (payment == null) {
            // Create a new Payment record if one doesn't exist.
            payment = new Payment();
            payment.setPrescription(updatedPrescription);
            // For simplicity, defaulting to CASH method and PAID status.
            payment.setMethod(PaymentMethod.CASH);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setTotalAmount(totalAmount);
            paymentRepository.save(payment);
        } else {
            // If payment already exists, update the total amount.
            payment.setTotalAmount(totalAmount);
            paymentRepository.save(payment);
        }

        return updatedPrescription;
    }

    @Override
    public Prescription getPrescriptionByBookingId(Long bookingId) {
        return prescriptionRepository.findByBookingAppointment_BookingId(bookingId)
            .orElseThrow(() -> new CustomSecurityException("Prescription not found for booking id " + bookingId, HttpStatus.NOT_FOUND));
    }

    @Override
    public Prescription getPrescriptionByToken(String token) {
        return prescriptionRepository.findByBookingAppointment_Token(token)
            .orElseThrow(() -> new CustomSecurityException("Prescription not found for token " + token, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatient_Id(patientId);
    }

    // Helper method: convert PrescriptionDto to Prescription entity.
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
        if (dto.getBookingAppointmentId() != null) {
            BookingAppointment booking = bookingAppointmentRepository.findById(dto.getBookingAppointmentId())
                .orElseThrow(() -> new CustomSecurityException("Booking appointment not found", HttpStatus.NOT_FOUND));
            prescription.setBookingAppointment(booking);
        }
        // Typically, prescription items are added separately.
        return prescription;
    }
}
