// InvoiceItemServiceImpl.java
package com.G19.hospital.service.implement.inventory.billing;

import com.G19.hospital.DTO.inventory.billing.CreateInvoiceItemDto;
import com.G19.hospital.DTO.inventory.billing.InvoiceItemDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Billing_Payment_Due.InvoiceItem;
import com.G19.hospital.repository.inventory.Prescription.DispenseTransactionRepository;
import com.G19.hospital.repository.inventory.billing.InvoiceItemRepository;
import com.G19.hospital.repository.inventory.billing.InvoiceRepository;
import com.G19.hospital.service.inventory.billing.InvoiceItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository itemRepo;
    private final InvoiceRepository invRepo;
    private final DispenseTransactionRepository dispRepo;

    @Override
    public InvoiceItemDto create(CreateInvoiceItemDto dto) {
        var inv = invRepo.findById(dto.getInvoiceId())
          .orElseThrow(() -> new CustomSecurityException("Invoice not found", HttpStatus.NOT_FOUND));
        var disp = dispRepo.findById(dto.getDispenseTransactionId())
          .orElseThrow(() -> new CustomSecurityException("DispenseTransaction not found", HttpStatus.NOT_FOUND));

        InvoiceItem ii = new InvoiceItem();
        ii.setInvoice(inv);
        ii.setDispenseTransaction(disp);
        ii.setDescription(dto.getDescription());
        ii.setQuantity(dto.getQuantity());
        ii.setUnitPrice(dto.getUnitPrice());
        ii.setLineTotal(dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        itemRepo.save(ii);

        // update parent totals
        inv.getItems().add(ii);
        invRepo.save(inv);

        return toDto(ii);
    }

    @Override
    public InvoiceItemDto update(Long id, CreateInvoiceItemDto dto) {
        InvoiceItem ii = itemRepo.findById(id)
          .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
        ii.setDescription(dto.getDescription());
        ii.setQuantity(dto.getQuantity());
        ii.setUnitPrice(dto.getUnitPrice());
        ii.setLineTotal(dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
        itemRepo.save(ii);
        // update parent totals...
        var inv = ii.getInvoice();
        invRepo.save(inv);
        return toDto(ii);
    }

    @Override
    public void delete(Long id) {
        InvoiceItem ii = itemRepo.findById(id)
          .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
        var inv = ii.getInvoice();
        itemRepo.delete(ii);
        invRepo.save(inv);
    }

    @Override
    @Transactional(readOnly=true)
    public InvoiceItemDto getById(Long id) {
        return itemRepo.findById(id).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("Item not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<InvoiceItemDto> getByInvoice(Long invoiceId, Pageable pg) {
        return itemRepo.findByInvoice_InvoiceId(invoiceId, pg).map(this::toDto);
    }

    @Override
    @Transactional(readOnly=true)
    public List<InvoiceItemDto> getByInvoice(Long invoiceId) {
        return itemRepo.findByInvoice_InvoiceId(invoiceId).stream()
          .map(this::toDto)
          .collect(Collectors.toList());
    }

    private InvoiceItemDto toDto(InvoiceItem ii) {
        return new InvoiceItemDto(
            ii.getItemId(),
            ii.getInvoice().getInvoiceId(),
            ii.getDispenseTransaction().getId(),
            ii.getDescription(),
            ii.getQuantity(),
            ii.getUnitPrice(),
            ii.getLineTotal()
        );
    }
}
