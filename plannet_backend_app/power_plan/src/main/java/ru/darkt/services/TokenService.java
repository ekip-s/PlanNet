package ru.darkt.services;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    public UUID getCurrentUserId() {
        return UUID.fromString(getRequiredClaim("sub"));
    }

    public String getCurrentUserLogin() {
        return getRequiredClaim("preferred_username");
    }

    private String getRequiredClaim(String claimName) {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || authentication.getToken() == null) {
            throw new AuthenticationServiceException("User not authenticated or token not present");
        }

        String claimValue = authentication.getToken().getClaim(claimName);
        if (claimValue == null) {
            throw new AuthenticationServiceException("Missing required claim: " + claimName);
        }

        return claimValue;
    }
}
