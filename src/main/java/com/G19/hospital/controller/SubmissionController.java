// src/main/java/com/G19/hospital/controller/SubmissionController.java
package com.G19.hospital.controller;

import com.G19.hospital.DTO.Questioneres.SubmissionResponseDto;
import com.G19.hospital.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @GetMapping
    public ResponseEntity<Page<SubmissionResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(submissionService.getAllSubmissions(pageable));
    }

    @GetMapping("/set/{setId}")
    public ResponseEntity<Page<SubmissionResponseDto>> getBySet(
            @PathVariable Long setId,
            Pageable pageable) {
        return ResponseEntity.ok(submissionService.getSubmissionsBySet(setId, pageable));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<SubmissionResponseDto>> getByUser(
            @PathVariable String username,
            Pageable pageable) {
        return ResponseEntity.ok(submissionService.getSubmissionsByUser(username, pageable));
    }


    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionResponseDto> getById(
            @PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionService.getSubmissionById(submissionId));
    }
}
