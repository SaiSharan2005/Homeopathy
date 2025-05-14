// QuestionRepository.java
package com.G19.hospital.repository.questionnere;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Questionner.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

