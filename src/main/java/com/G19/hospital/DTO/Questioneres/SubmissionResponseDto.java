// src/main/java/com/G19/hospital/DTO/Questioneres/SubmissionResponseDto.java
package com.G19.hospital.DTO.Questioneres;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubmissionResponseDto {
    private Long submissionId;
    private Instant submittedAt;
    private String username;
    private List<QuestionAnswerDto> answers;
}
