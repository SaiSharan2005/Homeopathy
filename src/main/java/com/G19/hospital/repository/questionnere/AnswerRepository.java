package com.G19.hospital.repository.questionnere;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Questionner.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}

