package com.G19.hospital.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdvertisementDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String targetPage;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate endDate;

    // Default constructor
    public AdvertisementDto() {}

    // Constructor with all fields
    public AdvertisementDto(Long id, String title, String description, String imageUrl, 
                          String targetPage, Boolean isActive, LocalDateTime createdAt, 
                          LocalDateTime updatedAt, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.targetPage = targetPage;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTargetPage() { return targetPage; }
    public void setTargetPage(String targetPage) { this.targetPage = targetPage; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
