package com.G19.hospital.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.service.ActivityLogService;
import com.G19.hospital.service.AdvertisementService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.G19.hospital.model.ActivityLog;
import com.G19.hospital.model.Advertisement;
import com.G19.hospital.repository.AdvertisementRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private ActivityLogService activityLogService;

     @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
    @Override
    public List<Advertisement> getAllAdvertisements() {
        return advertisementRepository.findAll();
    }
    @Override
    public Advertisement createAdvertisement(Advertisement advertisement) {
    // Create a new ActivityLog entry
    ActivityLog advertisementLog = new ActivityLog();
    advertisementLog.setMessage(String.format("Advertisement %d is created by %s (ID = %d)", 
                                              advertisement.getId(), "staff", 34));
    advertisementLog.setTimestamp(LocalDateTime.now()); // Set current timestamp
    advertisementLog.setUserId(34); // Assuming staff ID is 34
    advertisementLog.setUserType("Staff");

    // Log the activity
    activityLogService.createActivityLog(advertisementLog);
  

    // Save and return the advertisement
    return advertisementRepository.save(advertisement);
}

    @Override
    public Optional<Advertisement> getAdvertisementById(Long id) {
        return advertisementRepository.findById(id);
    }

    @Override
    public Advertisement updateAdvertisement(Long id, Advertisement updatedAd) {
        return advertisementRepository.findById(id).map(ad -> {
            // Update the advertisement fields
            ad.setTitle(updatedAd.getTitle());
            ad.setDescription(updatedAd.getDescription());
            ad.setImageUrl(updatedAd.getImageUrl());
            ad.setTargetPage(updatedAd.getTargetPage());
            ad.setIsActive(updatedAd.getIsActive());
    
            // Save the updated advertisement
            Advertisement savedAd = advertisementRepository.save(ad);
    
            // Create a new ActivityLog entry
            ActivityLog advertisementLog = new ActivityLog();
            advertisementLog.setMessage(String.format("Advertisement %d is updated by %s (ID = %d)", 
                                                      savedAd.getId(), "staff", 34));
            advertisementLog.setTimestamp(LocalDateTime.now()); // Set current timestamp
            advertisementLog.setUserId(34); // Assuming staff ID is 34
            advertisementLog.setUserType("Staff");
    
            // Log the activity
            activityLogService.createActivityLog(advertisementLog);
    
            return savedAd;
        }).orElseThrow(() -> new RuntimeException("Advertisement not found"));
    }
    

    @Override
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public void selectAdvertisement(Long id) {
        advertisementRepository.findById(id).ifPresent(ad -> {
            // Deactivate other ads for the same target page
            advertisementRepository.deactivateAllByTargetPage(ad.getTargetPage());
            // Activate the selected ad
            ad.setIsActive(true);
            advertisementRepository.save(ad);
        });
    }

    @Override
    public Advertisement getActiveAdsForPage(String targetPage) {
        return advertisementRepository.findByTargetPageAndIsActive(targetPage, true);
    }
    @Override
    public void changeStatus(Long id, Boolean isActive) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        advertisement.setIsActive(isActive);
        advertisementRepository.save(advertisement);
    }
}

