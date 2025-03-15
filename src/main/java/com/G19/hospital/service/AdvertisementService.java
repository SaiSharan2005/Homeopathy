package com.G19.hospital.service;

import com.G19.hospital.model.Advertisement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    String uploadImage(MultipartFile imageFile) throws IOException;

    List<Advertisement> getAllAdvertisements();

    Advertisement createAdvertisement(Advertisement advertisement);

    Optional<Advertisement> getAdvertisementById(Long id);

    Advertisement updateAdvertisement(Long id, Advertisement updatedAd);

    void deleteAdvertisement(Long id);

    void changeStatus(Long id, Boolean isActive);
}
