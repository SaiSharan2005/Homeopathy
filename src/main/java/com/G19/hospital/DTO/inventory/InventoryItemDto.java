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
    private String commonName;
    private String source;
    private String potency;
    private String formulation;
    private String description;
    private String manufacturer;
    private String unit;
    private int reorderLevel;
    private LocalDate expiryDate;
    private String storageConditions;
    private String indications;
    private String contraindications;
    private String sideEffects;
    private String usageInstructions;
    private String regulatoryStatus;
    private double costPrice;
    private double sellingPrice;
    // Reference to Category by its id
    private Long categoryId;
}
