// QuestionSetRepository.java
package com.G19.hospital.repository.questionnere;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.Questionner.QuestionSet;

public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {
    boolean existsByName(String name);
}



