package com.G19.hospital.controller;

import com.G19.hospital.DTO.AdvertisementDTO;
import com.G19.hospital.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;
  
 
    
        @Value("${file.upload-dir}")
        private String uploadDir; // Directory to store uploaded files, configured in application.properties
    
        @PostMapping("/upload")
        public String uploadFile(
                @RequestParam("file") MultipartFile file,
                @RequestParam("forwardLink") String forwardLink,
                @RequestParam("adminId") Long adminId) {
    
            if (file.isEmpty()) {
                return "Please select a file to upload.";
            }
    
            try {
                // Get the filename and generate a unique filename
                String originalFilename = file.getOriginalFilename();
                String filename = UUID.randomUUID().toString() + "_" + originalFilename;
    
                // Create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create directory including any necessary but nonexistent parent directories
                }
    
                // Save the file
                Path filePath = Paths.get(uploadDir, filename);
                Files.write(filePath, file.getBytes());
    
                // Construct the URL for accessing the uploaded file
                String fileUrl = "/api/advertisement/uploads/" + filename;
    
                // Handle file upload logic here
                return "File uploaded successfully: " + originalFilename + ". Access it using: " + fileUrl;
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to upload file.";
            }
        }
    

    @GetMapping("/{id}")
    public AdvertisementDTO getAdvertisementById(@PathVariable Long id) {
        return advertisementService.getAdvertisementById(id);
    }

    @GetMapping
    public List<AdvertisementDTO> getAllAdvertisements() {
        return advertisementService.getAllAdvertisements();
    }

    @PutMapping("/{id}")
    public AdvertisementDTO updateAdvertisement(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("forwardLink") String forwardLink,
            @RequestParam("adminId") Long adminId) {
        return advertisementService.updateAdvertisement(id, file, forwardLink, adminId);
    }

    @DeleteMapping("/{id}")
    public void deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
    }
}
