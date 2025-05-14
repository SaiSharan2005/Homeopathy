// src/main/java/com/G19/hospital/service/implement/QuestionSetServiceImpl.java
package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.Questioneres.QuestionDto;
import com.G19.hospital.DTO.Questioneres.QuestionSetDto;
import com.G19.hospital.model.Questionner.Question;
import com.G19.hospital.model.Questionner.QuestionSet;
import com.G19.hospital.repository.questionnere.QuestionSetRepository;
import com.G19.hospital.service.QuestionSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionSetServiceImpl implements QuestionSetService {

    private final QuestionSetRepository setRepo;

    @Override
    public QuestionSetDto createQuestionSet(QuestionSetDto dto) {
        if (setRepo.existsByName(dto.getName())) {
            throw new RuntimeException("A question set with name '" + dto.getName() + "' already exists");
        }
        QuestionSet set = new QuestionSet();
        set.setName(dto.getName());
        set.setDescription(dto.getDescription());
        dto.getQuestions().forEach(qdto -> {
            Question q = new Question();
            q.setText(qdto.getText());
            set.addQuestion(q);
        });
        return toDto(setRepo.save(set));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionSetDto> getAllSets(Pageable pageable) {
        return setRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionSetDto getSetById(Long id) {
        QuestionSet set = setRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Question set not found: " + id));
        return toDto(set);
    }

    private QuestionSetDto toDto(QuestionSet set) {
        QuestionSetDto dto = new QuestionSetDto();
        dto.setId(set.getId());
        dto.setName(set.getName());
        dto.setDescription(set.getDescription());
        dto.setQuestions(set.getQuestions().stream()
                .map(q -> new QuestionDto(q.getId(), q.getText()))
                .collect(Collectors.toList()));
        return dto;
    }
}

