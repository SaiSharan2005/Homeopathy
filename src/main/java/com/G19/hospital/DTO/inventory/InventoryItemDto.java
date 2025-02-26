package com.G19.hospital.DTO.inventory;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemDto {
    private Long id;
    private String name;
    private String description;
    private String manufacturer;
    private String unit;
    private int reorderLevel;
    private LocalDate expiryDate;
    // Reference to Category by its id
    private Long categoryId;
}
