package com.G19.hospital.service.implement;

import com.G19.hospital.DTO.UserLoginDto;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.AuthService;
import com.G19.hospital.util.Constants.ApiMessages;
import com.G19.hospital.util.Security.AccessToken;
import com.G19.hospital.util.Security.ITokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;
@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ITokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AccessToken register(UserRegisterDto userRegisterDto) {
           if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            throw new CustomSecurityException(
                ApiMessages.USERNAME_TAKEN, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new CustomSecurityException(
                ApiMessages.EMAIL_TAKEN, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByPhoneNumber(userRegisterDto.getPhoneNumber())) {
            throw new CustomSecurityException(
                ApiMessages.PHONE_TAKEN, HttpStatus.BAD_REQUEST);
        }
        try {
            // checkUserExistsWithUserName(userRegisterDto.getUsername());

            User user = new User();
            user.setEmail(userRegisterDto.getEmail());
            user.setUsername(userRegisterDto.getUsername());
            user.setPhoneNumber(userRegisterDto.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            user.setRoles(getRoles(userRegisterDto.getRoles()));

            String userId;
            Random random = new Random();
            String role = userRegisterDto.getRoles()[0].substring(0, 3);;  
            do {
                String firstNamePart = user.getUsername().substring(0,
                        Math.min(user.getUsername().length(), 4));
                int randomNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999
                userId = role + firstNamePart + randomNumber;
            } while (userRepository.existsByUserId(userId));

            user.setUserId(userId);
            userRepository.save(user);

            return tokenProvider.createToken(user.getUsername(), user.getRoles());
        } catch (Exception ex) {
            log.error("Error during user registration: {}", ex.getMessage(), ex);
            throw new CustomSecurityException(ApiMessages.REGISTRATION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

       @Override
    public AccessToken login(UserLoginDto loginDto) {
        // 1) Fetch by username (populated in controller)
        User user = userRepository.findByUsername(loginDto.getUsername())
            .orElseThrow(() -> 
                new CustomSecurityException(
                    ApiMessages.BAD_CREDENTIALS,
                    HttpStatus.BAD_REQUEST
                )
            );

        // 2) Verify password
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new CustomSecurityException(
                ApiMessages.BAD_CREDENTIALS,
                HttpStatus.BAD_REQUEST
            );
        }

        // 3) Authenticate with Spring Security
        try {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
                );
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            throw new CustomSecurityException(
                ApiMessages.BAD_CREDENTIALS,
                HttpStatus.BAD_REQUEST
            );
        }

        // 4) Generate JWT
        return tokenProvider.createToken(user.getUsername(), user.getRoles());
    }


    // private void checkUserExistsWithUserName(String username) {
    //     if (userRepository.existsByUsername(username)) {
    //         throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    //     }
    // }

    private Set<Role> getRoles(String[] roles) {
        try {
            Set<Role> userRoles = new HashSet<>();
            for (String role : roles) {
                Role roleEntity = roleRepository.findByName(role).get();
                if (roleEntity == null) {
                    throw new CustomSecurityException(String.format("Role '%s' not found", role), HttpStatus.BAD_REQUEST);
                }
                userRoles.add(roleEntity);
            }
            return userRoles;
        } catch (Exception ex) {
            log.error("Error fetching roles: {}", ex.getMessage(), ex);
            throw new CustomSecurityException(ApiMessages.ROLE_FETCH_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
