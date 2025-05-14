package com.G19.hospital.DTO.Questioneres;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSetDto {
    private Long id;                  // populated on reads
    private String name;
    private String description;
    private List<QuestionDto> questions;
}
