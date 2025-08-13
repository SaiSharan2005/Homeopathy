package com.G19.hospital.service;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.model.Advertisement;

public interface AdvertisementService {
    

    String uploadImage(MultipartFile imageFile) throws IOException;

    List<Advertisement> getAllAdvertisements();

    Advertisement createAdvertisement(Advertisement advertisement);

    Optional<Advertisement> getAdvertisementById(Long id);

    Advertisement updateAdvertisement(Long id, Advertisement updatedAd);

    void deleteAdvertisement(Long id);

    void selectAdvertisement(Long id);

    Advertisement getActiveAdsForPage(String targetPage);

    void changeStatus(Long id, Boolean isActive);

    // New methods for missing routes
    Page<Advertisement> getAdvertisementsWithPagination(int page, int size, String sortBy, String sortDir);
    
    List<Advertisement> getAdvertisementsByStatus(Boolean isActive);
    
    List<Advertisement> getAdvertisementsByTargetPage(String targetPage);
    
    void deleteMultipleAdvertisements(List<Long> ids);
    
    void changeBulkStatus(List<Long> ids, Boolean isActive);
    
    List<Advertisement> searchAdvertisements(String query);
    
    List<Advertisement> getExpiredAdvertisements();
    
    List<Advertisement> getAdvertisementsExpiringSoon(int days);
    
    Map<String, Object> getAdvertisementStats();
    
    Advertisement updateAdvertisementImage(Long id, MultipartFile image) throws IOException;
    
    Advertisement removeAdvertisementImage(Long id);
    
    Map<String, Object> validateAdvertisement(String title, String description, String targetPage, String endDate);
    
    Map<String, Object> getAdvertisementPreview(Long id);
    
    List<Map<String, Object>> getAdvertisementsWithActivity();
    
    void scheduleAdvertisement(Long id, String activationDate);
    
    List<Advertisement> getScheduledAdvertisements();

}

