package com.G19.hospital.model.Questionner;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_sets")
public class QuestionSet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    @Column private String description;

    @OneToMany(
      mappedBy = "questionSet",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
    )
    private Set<Question> questions = new HashSet<>();

    public void addQuestion(Question q) {
        q.setQuestionSet(this);
        questions.add(q);
    }
}
