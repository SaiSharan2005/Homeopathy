package com.G19.hospital.controller;

import com.G19.hospital.DTO.UserLoginDto;
import com.G19.hospital.model.User;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.AuthService;
import com.G19.hospital.util.Security.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(@RequestBody UserRegisterDto userRegisterDto) {
        AccessToken accessToken =  authService.register(userRegisterDto);
        return ResponseEntity.ok(accessToken);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        Optional<User> userData = userRepository.findByPhoneNumber(userLoginDto.getPhoneNumber());
    
        if (userData.isEmpty()) {
            return ResponseEntity.status(401).body("Login failed: User not found.");
        }
    
        userLoginDto.setUsername(userData.get().getUsername());
        AccessToken accessToken = authService.login(userLoginDto);
    
        return ResponseEntity.ok(accessToken);
    }
    
}
