package com.G19.hospital.model;

import jakarta.persistence.*;

// import .persistence.*;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertisementId;
    
    private String bannerImageLink;
    private String forwardLink;
    private Long adminId;

    // Getters and setters
    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getBannerImageLink() {
        return bannerImageLink;
    }

    public void setBannerImageLink(String bannerImageLink) {
        this.bannerImageLink = bannerImageLink;
    }

    public String getForwardLink() {
        return forwardLink;
    }

    public void setForwardLink(String forwardLink) {
        this.forwardLink = forwardLink;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
