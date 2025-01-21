package com.G19.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.G19.hospital.model.Advertisement;
import com.G19.hospital.service.AdvertisementService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping
    public List<Advertisement> getAllAdvertisements() {
        return advertisementService.getAllAdvertisements();
    }

    @PostMapping
    public ResponseEntity<Advertisement> createAdvertisement(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("targetPage") String targetPage,
        @RequestParam("image") MultipartFile image,
        @RequestParam("endDate") String endDate
    ) throws IOException {
        Advertisement ad = new Advertisement();
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setTargetPage(targetPage);
        ad.setImageUrl(advertisementService.uploadImage(image));
        ad.setEndDate(LocalDate.parse(endDate));

        return ResponseEntity.ok(advertisementService.createAdvertisement(ad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(
        @PathVariable Long id,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "targetPage", required = false) String targetPage,
        @RequestParam(value = "image", required = false) MultipartFile image,
        @RequestParam(value = "endDate", required = false) String endDate
    ) throws IOException {
        Optional<Advertisement> existingAd = advertisementService.getAdvertisementById(id);
        if (existingAd.isPresent()) {
            Advertisement ad = existingAd.get();
            if (title != null) ad.setTitle(title);
            if (description != null) ad.setDescription(description);
            if (targetPage != null) ad.setTargetPage(targetPage);
            if (image != null) ad.setImageUrl(advertisementService.uploadImage(image));
            if (endDate != null) ad.setEndDate(LocalDate.parse(endDate));

            return ResponseEntity.ok(advertisementService.updateAdvertisement(id, ad));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
    }
}
