// src/main/java/com/G19/hospital/service/implement/SubmissionServiceImpl.java
package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.Questioneres.QuestionAnswerDto;
import com.G19.hospital.DTO.Questioneres.SubmissionRequestDto;
import com.G19.hospital.DTO.Questioneres.SubmissionResponseDto;
import com.G19.hospital.model.*;
import com.G19.hospital.model.Questionner.Answer;
import com.G19.hospital.model.Questionner.Question;
import com.G19.hospital.model.Questionner.QuestionSet;
import com.G19.hospital.model.Questionner.Submission;
import com.G19.hospital.repository.*;
import com.G19.hospital.repository.questionnere.QuestionRepository;
import com.G19.hospital.repository.questionnere.QuestionSetRepository;
import com.G19.hospital.repository.questionnere.SubmissionRepository;
import com.G19.hospital.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final UserRepository userRepo;
    private final QuestionSetRepository setRepo;
    private final QuestionRepository questionRepo;
    private final SubmissionRepository submissionRepo;

    @Override
    public SubmissionResponseDto submitAnswers(Long questionSetId, SubmissionRequestDto dto) {
        User user = userRepo.findByUsername(dto.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUsername()));

        QuestionSet set = setRepo.findById(questionSetId)
            .orElseThrow(() -> new RuntimeException("Question set not found: " + questionSetId));

        Submission submission = new Submission();
        submission.setUser(user);
        submission.setQuestionSet(set);

        dto.getAnswers().forEach(qa -> {
            Question q = questionRepo.findById(qa.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found: " + qa.getQuestionId()));
            Answer ans = new Answer();
            ans.setQuestion(q);
            ans.setResponse(qa.getResponse());
            submission.addAnswer(ans);
        });

        Submission saved = submissionRepo.save(submission);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponseDto> getAllSubmissions(Pageable pageable) {
        return submissionRepo.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponseDto> getSubmissionsBySet(Long questionSetId, Pageable pageable) {
        return submissionRepo.findByQuestionSetId(questionSetId, pageable).map(this::toDto);
    }
    @Override
    @Transactional(readOnly = true)




    
    public SubmissionResponseDto getSubmissionById(Long submissionId) {
        Submission submission = submissionRepo.findById(submissionId)
            .orElseThrow(() -> new RuntimeException("Submission not found: " + submissionId));
        return toDto(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponseDto> getSubmissionsByUser(String username, Pageable pageable) {
        return submissionRepo.findByUserUsername(username, pageable).map(this::toDto);
    }

    private SubmissionResponseDto toDto(Submission submission) {
        return new SubmissionResponseDto(
            submission.getId(),
            submission.getSubmittedAt(),
            submission.getUser().getUsername(),
            submission.getAnswers().stream()
                .map(a -> new QuestionAnswerDto(a.getQuestion().getId(),a.getQuestion().getText(),  a.getResponse()))
                .collect(Collectors.toList())
        );
    }
}
