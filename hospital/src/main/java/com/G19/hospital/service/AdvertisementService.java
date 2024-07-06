package com.G19.hospital.service;

import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.DTO.AdvertisementDTO;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO createAdvertisement(MultipartFile file, String forwardLink, Long adminId);
    AdvertisementDTO getAdvertisementById(Long id);
    List<AdvertisementDTO> getAllAdvertisements();
    AdvertisementDTO updateAdvertisement(Long id, MultipartFile file, String forwardLink, Long adminId);
    void deleteAdvertisement(Long id);
}
