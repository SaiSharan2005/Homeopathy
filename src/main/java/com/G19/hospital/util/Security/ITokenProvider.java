package com.G19.hospital.util.Security;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.G19.hospital.model.Role;

import java.util.Set;

public interface ITokenProvider {


    AccessToken createToken(String username, Set<Role> roles);
    boolean validateToken(AccessToken accessToken);
    AccessToken getTokenFromHeader(HttpServletRequest httpServletRequest);
    Authentication getAuthentication(AccessToken accessToken);
}
