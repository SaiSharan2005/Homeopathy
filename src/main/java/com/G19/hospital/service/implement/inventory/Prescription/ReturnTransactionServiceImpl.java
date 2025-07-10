// src/main/java/com/G19/hospital/service/implement/prescription/ReturnTransactionServiceImpl.java
package com.G19.hospital.service.implement.inventory.Prescription;

import com.G19.hospital.DTO.inventory.Prescription.CreateReturnDto;
import com.G19.hospital.DTO.inventory.Prescription.ReturnDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.User;
import com.G19.hospital.model.inventory.StockAndBatchTracking.StockLevel;
import com.G19.hospital.model.inventory.prescription.DispenseTransaction;
import com.G19.hospital.model.inventory.prescription.ReturnTransaction;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.inventory.Prescription.DispenseTransactionRepository;
import com.G19.hospital.repository.inventory.Prescription.ReturnTransactionRepository;
import com.G19.hospital.repository.inventory.StockAndBatchTracking.StockLevelRepository;
import com.G19.hospital.service.inventory.Prescription.ReturnTransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReturnTransactionServiceImpl implements ReturnTransactionService {

    private final ReturnTransactionRepository returnRepo;
    private final DispenseTransactionRepository dispenseRepo;
    private final UserRepository userRepo;
    private final StockLevelRepository stockLevelRepo;

    @Override
    public ReturnDto create(CreateReturnDto dto) {
        DispenseTransaction disp = dispenseRepo.findById(dto.getDispenseTransactionId())
            .orElseThrow(() -> new CustomSecurityException("DispenseTransaction not found", HttpStatus.NOT_FOUND));
        User user = userRepo.findById(dto.getReturnedById())
            .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));

        // validate quantity
        if (dto.getQuantityReturned() > disp.getRxItem().getQuantity()) {
            throw new CustomSecurityException("Return quantity exceeds dispensed quantity", HttpStatus.BAD_REQUEST);
        }

        ReturnTransaction rt = new ReturnTransaction();
        rt.setDispenseTransaction(disp);
        rt.setReturnedBy(user);
        rt.setReturnDate(dto.getReturnDate() != null ? dto.getReturnDate() : LocalDateTime.now());
        rt.setQuantityReturned(dto.getQuantityReturned());
        rt.setReason(dto.getReason());

        ReturnTransaction saved = returnRepo.save(rt);

        // **Adjust stock level**: add returned quantity back to stock
        // Find the stock level for this batch & warehouse
        StockLevel sl = stockLevelRepo
            .findFirstByBatch_IdAndWarehouse_Id(
                disp.getBatch().getId(),
                disp.getWarehouse().getId()
            ).orElseThrow(() -> new CustomSecurityException(
                "StockLevel not found for returned batch", HttpStatus.INTERNAL_SERVER_ERROR));

        sl.setQuantityOnHand(sl.getQuantityOnHand() + dto.getQuantityReturned());
        stockLevelRepo.save(sl);

        return toDto(saved);
    }

    @Override
    public ReturnDto update(Long id, CreateReturnDto dto) {
        ReturnTransaction rt = returnRepo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("ReturnTransaction not found", HttpStatus.NOT_FOUND));

        // Note: if quantity changes, you'd need to re-adjust stockDelta = new-old
        // For simplicity, disallow quantity change here or implement reversal logic.

        rt.setReason(dto.getReason());
        rt.setReturnDate(dto.getReturnDate() != null ? dto.getReturnDate() : rt.getReturnDate());

        return toDto(returnRepo.save(rt));
    }

    @Override
    public void delete(Long id) {
        ReturnTransaction rt = returnRepo.findById(id)
            .orElseThrow(() -> new CustomSecurityException("ReturnTransaction not found", HttpStatus.NOT_FOUND));
        // Optionally reverse stock adjustment here...
        returnRepo.delete(rt);
    }

    @Override
    @Transactional(readOnly = true)
    public ReturnDto getById(Long id) {
        return returnRepo.findById(id).map(this::toDto)
          .orElseThrow(() -> new CustomSecurityException("ReturnTransaction not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnDto> getAll(Pageable pageable) {
        return returnRepo.findAll(pageable).map(this::toDto);
    }

    private ReturnDto toDto(ReturnTransaction rt) {
        return new ReturnDto(
            rt.getId(),
            rt.getDispenseTransaction().getId(),
            rt.getReturnedBy().getId(),
            rt.getReturnDate(),
            rt.getQuantityReturned(),
            rt.getReason()
        );
    }
}
