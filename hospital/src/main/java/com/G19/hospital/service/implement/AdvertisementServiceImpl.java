package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.AdvertisementDTO;
import com.G19.hospital.model.Advertisement;
import com.G19.hospital.repository.AdvertisementRepository;
import com.G19.hospital.service.AdvertisementService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    private final String uploadDir = "uploads/";

    @Override
    public AdvertisementDTO createAdvertisement(MultipartFile file, String forwardLink, Long adminId) {
        String bannerImageLink = saveFile(file);
        Advertisement advertisement = new Advertisement();
        advertisement.setBannerImageLink(bannerImageLink);
        advertisement.setForwardLink(forwardLink);
        advertisement.setAdminId(adminId);
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);
        return mapToDTO(savedAdvertisement);
    }

    @Override
    public AdvertisementDTO getAdvertisementById(Long id) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        return advertisement.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return advertisements.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AdvertisementDTO updateAdvertisement(Long id, MultipartFile file, String forwardLink, Long adminId) {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findById(id);
        if (optionalAdvertisement.isPresent()) {
            Advertisement advertisement = optionalAdvertisement.get();
            if (file != null && !file.isEmpty()) {
                String bannerImageLink = saveFile(file);
                advertisement.setBannerImageLink(bannerImageLink);
            }
            advertisement.setForwardLink(forwardLink);
            advertisement.setAdminId(adminId);
            Advertisement updatedAdvertisement = advertisementRepository.save(advertisement);
            return mapToDTO(updatedAdvertisement);
        }
        return null;
    }

    @Override
    public void deleteAdvertisement(Long id) {
        advertisementRepository.deleteById(id);
    }

    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);
            return uploadDir + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private AdvertisementDTO mapToDTO(Advertisement advertisement) {
        AdvertisementDTO dto = new AdvertisementDTO();
        dto.setAdvertisementId(advertisement.getAdvertisementId());
        dto.setBannerImageLink(advertisement.getBannerImageLink());
        dto.setForwardLink(advertisement.getForwardLink());
        dto.setAdminId(advertisement.getAdminId());
        return dto;
    }
}
