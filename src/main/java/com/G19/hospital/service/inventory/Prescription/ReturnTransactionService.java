// src/main/java/com/G19/hospital/service/prescription/ReturnTransactionService.java
package com.G19.hospital.service.inventory.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.G19.hospital.DTO.inventory.Prescription.CreateReturnDto;
import com.G19.hospital.DTO.inventory.Prescription.ReturnDto;

public interface ReturnTransactionService {
    ReturnDto create(CreateReturnDto dto);
    ReturnDto update(Long id, CreateReturnDto dto);
    void delete(Long id);
    ReturnDto getById(Long id);
    Page<ReturnDto> getAll(Pageable pageable);
}
