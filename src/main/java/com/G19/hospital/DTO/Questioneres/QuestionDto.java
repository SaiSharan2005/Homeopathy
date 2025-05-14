package com.G19.hospital.DTO.Questioneres;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;      // will come back populated on reads
    private String text;  // required on create
}
