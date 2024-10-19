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
        checkUserExistsWithUserName(userRegisterDto.getUsername());

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPhoneNumber(userRegisterDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRoles(getRoles(userRegisterDto.getRoles()));

        userRepository.save(user);

        String username = user.getUsername();
        Set<Role> roles = user.getRoles();

        return tokenProvider.createToken(username,roles);
    }



@Override
public AccessToken login(UserLoginDto userLoginDto) {
    String username = userLoginDto.getUsername();
    String password = userLoginDto.getPassword();

    // Load user details from UserDetailsService
    Optional<User> userDetails = userRepository.findByUsername(username);
    
    // Check if user exists and if the password matches
    if (userDetails == null) {
        throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }
    User userDetail = userDetails.get();
    // Verify the password against the stored password
    if (!passwordEncoder.matches(password, userDetail.getPassword())) {
        throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }

    // Authenticate the user
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // Retrieve roles from user details
        Set<Role> roles = userRepository.findByUsername(username).get().getRoles();
        // Generate and return the access token
        return tokenProvider.createToken(username, roles);
    } catch (AuthenticationException exception) {
        throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }
}

    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
        }
    }
    private Set<Role> getRoles(String [] roles){
        Set<Role> userRoles = new HashSet<>();
        for(String role : roles) {
            userRoles.add(roleRepository.findByName(role));
        }
        return userRoles;
    }

}
