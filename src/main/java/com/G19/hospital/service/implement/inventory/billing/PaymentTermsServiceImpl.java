// src/main/java/com/G19/hospital/service/implement/billing/PaymentTermsServiceImpl.java
package com.G19.hospital.service.implement.inventory.billing;

import com.G19.hospital.DTO.inventory.billing.CreatePaymentTermsDto;
import com.G19.hospital.DTO.inventory.billing.PaymentTermsDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Billing_Payment_Due.PaymentTerms;
import com.G19.hospital.repository.inventory.billing.PaymentTermsRepository;
import com.G19.hospital.service.inventory.billing.PaymentTermsService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentTermsServiceImpl implements PaymentTermsService {

    private final PaymentTermsRepository repo;

    @Override
    public PaymentTermsDto create(CreatePaymentTermsDto dto) {
        PaymentTerms pt = new PaymentTerms();
        pt.setName(dto.getName());
        pt.setDaysUntilDue(dto.getDaysUntilDue());
        PaymentTerms saved = repo.save(pt);
        return toDto(saved);
    }

    @Override
    public PaymentTermsDto update(Long id, CreatePaymentTermsDto dto) {
        PaymentTerms pt = repo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("PaymentTerms not found", HttpStatus.NOT_FOUND));
        pt.setName(dto.getName());
        pt.setDaysUntilDue(dto.getDaysUntilDue());
        PaymentTerms updated = repo.save(pt);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new CustomSecurityException("PaymentTerms not found", HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentTermsDto getById(Long id) {
        return repo.findById(id)
            .map(this::toDto)
            .orElseThrow(() -> new CustomSecurityException("PaymentTerms not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentTermsDto> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    private PaymentTermsDto toDto(PaymentTerms pt) {
        return new PaymentTermsDto(
            pt.getPaymentTermsId(),
            pt.getName(),
            pt.getDaysUntilDue()
        );
    }
}
