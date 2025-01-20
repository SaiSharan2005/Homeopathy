package com.G19.hospital.service.implement;

import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.DTO.UserLoginDto;
import com.G19.hospital.DTO.UserRegisterDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
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
        try {
            checkUserExistsWithUserName(userRegisterDto.getUsername());

            User user = new User();
            user.setEmail(userRegisterDto.getEmail());
            user.setUsername(userRegisterDto.getUsername());
            user.setPhoneNumber(userRegisterDto.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            user.setRoles(getRoles(userRegisterDto.getRoles()));

            String userId;
            Random random = new Random();
            do {
                String firstNamePart = user.getUsername().substring(0,
                        Math.min(user.getUsername().length(), 4));
                int randomNumber = random.nextInt(9000) + 1000; // Random number between 1000 and 9999
                userId = "D29" + firstNamePart + randomNumber;
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
    public AccessToken login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        try {
            // Load user details
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
            }

            User user = optionalUser.get();

            // Verify the password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
            }

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Generate and return the token
            return tokenProvider.createToken(user.getUsername(), user.getRoles());
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user {}: {}", username, ex.getMessage(), ex);
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Unexpected error during login for user {}: {}", username, ex.getMessage(), ex);
            throw new CustomSecurityException(ApiMessages.LOGIN_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    private Set<Role> getRoles(String[] roles) {
        try {
            Set<Role> userRoles = new HashSet<>();
            for (String role : roles) {
                Role roleEntity = roleRepository.findByName(role);
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
