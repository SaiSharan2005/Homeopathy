package com.G19.hospital.controller;

import com.G19.hospital.DTO.UserLoginDto;
import com.G19.hospital.model.User;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.AuthService;
import com.G19.hospital.util.Constants.ApiMessages;
import com.G19.hospital.util.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Cloudinary cloudinary;


    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(@RequestBody UserRegisterDto userRegisterDto) {
        AccessToken accessToken =  authService.register(userRegisterDto);
        return ResponseEntity.ok(accessToken);
    }

  @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@Valid @RequestBody UserLoginDto loginDto) {
        // 1) Lookup by phone number
        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber())
            .orElseThrow(() ->
                new CustomSecurityException(
                    ApiMessages.BAD_CREDENTIALS,
                    HttpStatus.BAD_REQUEST
                )
            );

        // 2) Transfer username into DTO and delegate to AuthService
        loginDto.setUsername(user.getUsername());
        AccessToken token = authService.login(loginDto);

        return ResponseEntity.ok(token);
    }
    
    @PostMapping("/addProfilePic")
public ResponseEntity<?> addProfilePic(@RequestParam("image") MultipartFile image) {
    try {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // assuming username is the unique identifier
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Upload the image using your Cloudinary service
        // You could inject a dedicated CloudinaryService similar to advertisementService.uploadImage(image)
        String imageUrl = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap())
                .get("url").toString();

        // Update the user's profile picture URL and save
        user.setImageUrl(imageUrl);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Profile picture update failed: " + e.getMessage());
    }
}
@GetMapping("/me")
public ResponseEntity<?> getAuthenticatedDetails() {
    try {
        // Get the currently authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming phone number is used as the username

        // Retrieve the patient information based on the authenticated phone number
        Optional<User> user = userRepository.findByUsername(username);
        return ResponseEntity.ok(user);
    } catch (Exception e) {
        return ResponseEntity.status(404).body("User not found: " + e.getMessage());
    }
}

}
