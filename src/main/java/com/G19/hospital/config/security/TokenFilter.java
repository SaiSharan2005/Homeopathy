package com.G19.hospital.config.security;

import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.util.Security.AccessToken;
import com.G19.hospital.util.Security.ITokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;


@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private ITokenProvider tokenProvider;

    @Override

    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        AccessToken accessToken = tokenProvider.getTokenFromHeader(httpServletRequest);
        try {
            if (checkAccessToken(accessToken)) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        // Catch CustomSecurityException Before Global Exception Handler for clearing Context
        catch (CustomSecurityException customSecurityException) {
            SecurityContextHolder.clearContext();
            // throw again
            throw customSecurityException;
        }

    }
    private boolean checkAccessToken(AccessToken accessToken) {
        if (accessToken == null) return false;
        return tokenProvider.validateToken(accessToken);
    }
}
