package com.G19.hospital.DTO.Questioneres;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class QuestionAnswerDto {
    private Long questionId;
    private String questionText;    // ← new field

    private String response;
}
