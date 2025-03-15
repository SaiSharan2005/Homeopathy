package com.G19.hospital.DTO.inventory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    // Optionally include id if you want to send it back to the client
    private Long id;
    private String name;
    private String description;
}
