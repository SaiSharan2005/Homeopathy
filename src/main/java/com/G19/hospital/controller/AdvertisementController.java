package com.G19.hospital.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.model.Advertisement;
import com.G19.hospital.service.AdvertisementService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ads")
public class AdvertisementController {

    private final String uploadDir = "uploads/";

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping
    public List<Advertisement> getAllAdvertisements() {
        return advertisementService.getAllAdvertisements();
    }

  @PostMapping
    public ResponseEntity<Advertisement> createAd(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("targetPage") String targetPage,
        @RequestParam("image") MultipartFile image
    ) throws IOException {

        String imageUrl = advertisementService.uploadImage(image);
        Advertisement ad = new Advertisement();
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setTargetPage(targetPage);
        ad.setImageUrl(imageUrl);
        // advertisementRepository.save(ad);
  
        return ResponseEntity.ok(advertisementService.createAdvertisement(ad));
    }

    @GetMapping("/{id}")
    public Advertisement getAdvertisementById(@PathVariable Long id) {
        return advertisementService.getAdvertisementById(id)
            .orElseThrow(() -> new RuntimeException("Advertisement not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(
        @PathVariable Long id,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "targetPage", required = false) String targetPage,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Optional<Advertisement> existingAd = advertisementService.getAdvertisementById(id);
        if (existingAd.isPresent()) {
            Advertisement ad = existingAd.get();
            if (title != null) ad.setTitle(title);
            if (description != null) ad.setDescription(description);
            if (targetPage != null) ad.setTargetPage(targetPage);
            if (image != null) {
                String imageUrl = advertisementService.uploadImage(image);
                ad.setImageUrl(imageUrl);
            }
            Advertisement updatedAd = advertisementService.updateAdvertisement(id, ad);
            return ResponseEntity.ok(updatedAd);
        } else {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{id}")
    public void deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
    }

    @PatchMapping("/select/{id}")
    public void selectAdvertisement(@PathVariable Long id) {
        advertisementService.selectAdvertisement(id);
    }

    @GetMapping("/active")
    public Advertisement getActiveAds(@RequestParam String targetPage) {
        return advertisementService.getActiveAdsForPage(targetPage);
    }
    @GetMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        advertisementService.changeStatus(id, isActive);
        return ResponseEntity.ok().build();
    }
}

