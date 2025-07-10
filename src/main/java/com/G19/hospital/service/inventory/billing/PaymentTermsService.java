// src/main/java/com/G19/hospital/service/billing/PaymentTermsService.java
package com.G19.hospital.service.inventory.billing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.billing.CreatePaymentTermsDto;
import com.G19.hospital.DTO.inventory.billing.PaymentTermsDto;

public interface PaymentTermsService {
    PaymentTermsDto create(CreatePaymentTermsDto dto);
    PaymentTermsDto update(Long id, CreatePaymentTermsDto dto);
    void delete(Long id);
    PaymentTermsDto getById(Long id);
    Page<PaymentTermsDto> getAll(Pageable pageable);
}
