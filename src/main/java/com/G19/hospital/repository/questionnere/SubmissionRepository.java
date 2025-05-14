package com.G19.hospital.repository.questionnere;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Questionner.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Page<Submission> findByQuestionSetId(Long questionSetId, Pageable pageable);
    Page<Submission> findByUserUsername(String username, Pageable pageable);

}
