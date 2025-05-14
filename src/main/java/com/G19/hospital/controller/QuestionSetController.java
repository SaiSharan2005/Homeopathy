// src/main/java/com/G19/hospital/controller/QuestionSetController.java
package com.G19.hospital.controller;

import com.G19.hospital.DTO.Questioneres.QuestionSetDto;
import com.G19.hospital.DTO.Questioneres.SubmissionRequestDto;
import com.G19.hospital.DTO.Questioneres.SubmissionResponseDto;
import com.G19.hospital.service.QuestionSetService;
import com.G19.hospital.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question-sets")
@RequiredArgsConstructor
public class QuestionSetController {

    private final QuestionSetService setService;
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<QuestionSetDto> createSet(@RequestBody QuestionSetDto dto) {
        return ResponseEntity.ok(setService.createQuestionSet(dto));
    }

    @GetMapping
    public ResponseEntity<Page<QuestionSetDto>> allSets(Pageable pageable) {
        return ResponseEntity.ok(setService.getAllSets(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionSetDto> getSet(@PathVariable Long id) {
        return ResponseEntity.ok(setService.getSetById(id));
    }

    @GetMapping("/{id}/submissions")
    public ResponseEntity<Page<SubmissionResponseDto>> getSubmissionsBySet(
            @PathVariable Long id,
            Pageable pageable) {
        return ResponseEntity.ok(submissionService.getSubmissionsBySet(id, pageable));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<SubmissionResponseDto> submitAnswers(
            @PathVariable Long id,
            @RequestBody SubmissionRequestDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        dto.setUsername(auth.getName());
        return ResponseEntity.ok(submissionService.submitAnswers(id, dto));
    }
}
