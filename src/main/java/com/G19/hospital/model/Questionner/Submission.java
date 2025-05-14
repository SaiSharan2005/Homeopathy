package com.G19.hospital.model.Questionner;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.G19.hospital.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // which user submitted
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    
    @JoinColumn(name = "user_id")
    private User user;

    // which question‑set they answered
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_set_id")
    private QuestionSet questionSet;

    @Column(nullable = false, updatable = false)
    private Instant submittedAt = Instant.now();

    @OneToMany(
      mappedBy = "submission",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
    )
    private Set<Answer> answers = new HashSet<>();

    /** convenience to wire bi‑directional */
    public void addAnswer(Answer a) {
        a.setSubmission(this);
        answers.add(a);
    }
}
