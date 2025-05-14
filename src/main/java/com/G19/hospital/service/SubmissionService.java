// src/main/java/com/G19/hospital/service/SubmissionService.java
package com.G19.hospital.service;

import com.G19.hospital.DTO.Questioneres.SubmissionRequestDto;
import com.G19.hospital.DTO.Questioneres.SubmissionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubmissionService {
    SubmissionResponseDto submitAnswers(Long questionSetId, SubmissionRequestDto dto);
    Page<SubmissionResponseDto> getAllSubmissions(Pageable pageable);
    Page<SubmissionResponseDto> getSubmissionsBySet(Long questionSetId, Pageable pageable);
    Page<SubmissionResponseDto> getSubmissionsByUser(String username, Pageable pageable);
    SubmissionResponseDto getSubmissionById(Long submissionId);

}
