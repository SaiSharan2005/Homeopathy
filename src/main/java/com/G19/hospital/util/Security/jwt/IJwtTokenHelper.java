package com.G19.hospital.util.Security.jwt;

import com.G19.hospital.model.Role;
import com.G19.hospital.util.Security.AccessToken;
import com.G19.hospital.util.Security.SecretKey;

import java.util.Set;

public interface IJwtTokenHelper {
    String generateJwtToken(SecretKey secretKey, String username, Set<Role> roles);
    boolean validateJwtToken(SecretKey secretKey,AccessToken accessToken);
    String getUsernameFromJwtToken(SecretKey secretKey,AccessToken accessToken);
}
