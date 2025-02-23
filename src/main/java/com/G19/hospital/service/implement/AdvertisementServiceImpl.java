package com.G19.hospital.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.model.Advertisement;
import com.G19.hospital.repository.AdvertisementRepository;
import com.G19.hospital.service.AdvertisementService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
    private Cloudinary cloudinary;

    @Override
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
        advertisement.setCreatedAt(LocalDateTime.now());
        return advertisementRepository.save(advertisement);
    }

    @Override
    public Optional<Advertisement> getAdvertisementById(Long id) {
        return advertisementRepository.findById(id);
    }

    @Override
    public Advertisement updateAdvertisement(Long id, Advertisement updatedAd) {
        return advertisementRepository.findById(id).map(ad -> {
            ad.setTitle(updatedAd.getTitle());
            ad.setDescription(updatedAd.getDescription());
            ad.setImageUrl(updatedAd.getImageUrl());
            ad.setTargetPage(updatedAd.getTargetPage());
            ad.setEndDate(updatedAd.getEndDate());
            ad.setIsActive(updatedAd.getIsActive());
            ad.setUpdatedAt(LocalDateTime.now());
            return advertisementRepository.save(ad);
        }).orElseThrow(() -> new RuntimeException("Advertisement not found"));
    }

    @Override
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id, Boolean isActive) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        advertisement.setIsActive(isActive);
        advertisement.setUpdatedAt(LocalDateTime.now());
        advertisementRepository.save(advertisement);
 
    }
//     @Scheduled(cron = "0 0 * * * *") // Runs every hour
//     public void deactivateExpiredAdvertisements() {
//         List<Advertisement> ads = advertisementRepository.findAll();
//         for (Advertisement ad : ads) {
//             if (ad.getEndDate() != null && ad.getEndDate().isBefore(LocalDateTime.now())) {
//                 ad.setIsActive(false);
//                 advertisementRepository.save(ad);
//             }
//         }
//     }
}
