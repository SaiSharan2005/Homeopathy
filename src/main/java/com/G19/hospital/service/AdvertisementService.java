package com.G19.hospital.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

}

