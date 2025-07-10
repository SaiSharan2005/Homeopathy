// InvoiceServiceImpl.java
package com.G19.hospital.service.implement.inventory.billing;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceItemDto;
import com.G19.hospital.DTO.inventory.billing.RecordPaymentDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.Billing_Payment_Due.Invoice;
import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceItem;
import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceStatus;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import com.G19.hospital.service.inventory.billing.InvoiceService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final UserRepository userRepo;

    @Override
    public InvoiceDto create(CreateInvoiceDto dto) {
        User patient = userRepo.findById(dto.getPatientId())
          .orElseThrow(() -> new CustomSecurityException("Patient not found", HttpStatus.NOT_FOUND));

        Invoice inv = new Invoice();
        inv.setPatient(patient);
        inv.setIssueDate(dto.getIssueDate() != null ? dto.getIssueDate() : LocalDate.now());
        inv.setDueDate(dto.getDueDate());
        inv.setStatus(dto.getStatus() != null ? dto.getStatus() : InvoiceStatus.PENDING);

        // map items
        var items = dto.getItems().stream().map(ci -> {
            InvoiceItem ii = new InvoiceItem();
            ii.setInvoice(inv);
            ii.setDispenseTransaction(/* fetch DispenseTransaction via repository */ null);
            ii.setDescription(ci.getDescription());
            ii.setQuantity(ci.getQuantity());
            ii.setUnitPrice(ci.getUnitPrice());
            ii.setLineTotal(ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
            return ii;
        }).collect(Collectors.toSet());
        inv.getItems().addAll(items);

        // compute totals
        computeTotals(inv);
        invoiceRepo.save(inv);

        return toDto(inv);
    }

    @Override
    public InvoiceDto update(Long id, CreateInvoiceDto dto) {
        Invoice inv = invoiceRepo.findById(id)
          .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
        if (dto.getDueDate() != null) inv.setDueDate(dto.getDueDate());
        if (dto.getStatus() != null) inv.setStatus(dto.getStatus());
        // TODO: update items if dto.getItems()!=null
        computeTotals(inv);
        invoiceRepo.save(inv);
        return toDto(inv);
    }

    @Override
    public void delete(Long id) {
        if (!invoiceRepo.existsById(id)) throw new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND);
        invoiceRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public InvoiceDto getById(Long id) {
        return invoiceRepo.findById(id).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<InvoiceDto> getAll(Pageable pg) {
        return invoiceRepo.findAll(pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public Page<InvoiceDto> getByPatient(Long pid, Pageable pg) {
        return invoiceRepo.findByPatient_Id(pid, pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public Page<InvoiceDto> getByStatus(String status, Pageable pg) {
        InvoiceStatus st = InvoiceStatus.valueOf(status.toUpperCase());
        return invoiceRepo.findByStatus(st, pg).map(this::toDto);
    }

    @Override
    public InvoiceDto recordPayment(RecordPaymentDto dto) {
        Invoice inv = invoiceRepo.findById(dto.getInvoiceId())
          .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
        inv.setAmountPaid(inv.getAmountPaid().add(dto.getPaymentAmount()));
        inv.setAmountDue(inv.getTotalAmount().subtract(inv.getAmountPaid()));
        if (inv.getAmountDue().compareTo(BigDecimal.ZERO) <= 0) {
            inv.setStatus(InvoiceStatus.PAID);
        }
        invoiceRepo.save(inv);
        return toDto(inv);
    }

    private void computeTotals(Invoice inv) {
        BigDecimal total = inv.getItems().stream()
            .map(InvoiceItem::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        inv.setTotalAmount(total);
        inv.setAmountDue(total.subtract(inv.getAmountPaid()));
    }

    private InvoiceDto toDto(Invoice inv) {
         List<InvoiceItemDto> items = inv.getItems().stream()
        .map(ii -> new InvoiceItemDto(
            ii.getItemId(),
            inv.getInvoiceId(),
            ii.getDispenseTransaction().getId(),
            ii.getDescription(),
            ii.getQuantity(),
            ii.getUnitPrice(),
            ii.getLineTotal()
        ))
        .collect(Collectors.toList());

    // Now call the InvoiceDto constructor that takes exactly these types
    return new InvoiceDto(
        inv.getInvoiceId(),           // Long
        inv.getPatient().getId(),     // Long
        inv.getIssueDate(),           // LocalDate
        inv.getDueDate(),             // LocalDate
        inv.getTotalAmount(),         // BigDecimal
        inv.getAmountPaid(),          // BigDecimal
        inv.getAmountDue(),           // BigDecimal
        inv.getStatus(),              // InvoiceStatus
        items                         // List<InvoiceItemDto>
    );

    }
}
