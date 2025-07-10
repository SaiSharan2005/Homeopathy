// src/main/java/com/G19/hospital/service/QuestionSetService.java
package com.G19.hospital.service;

import com.G19.hospital.DTO.Questioneres.QuestionSetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QuestionSetService {
    QuestionSetDto createQuestionSet(QuestionSetDto dto);
    Page<QuestionSetDto> getAllSets(Pageable pageable);
    QuestionSetDto getSetById(Long id);
}
