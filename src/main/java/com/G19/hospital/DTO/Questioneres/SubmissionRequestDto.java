package com.G19.hospital.DTO.Questioneres;

import lombok.*;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class SubmissionRequestDto {
    private String username;
    private List<QuestionAnswerDto> answers;
}
