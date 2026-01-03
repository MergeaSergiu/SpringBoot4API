package dev.spring.API.helper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class HelperUtils {

    public String extractUsername(Authentication authentication) {
        String principal = null;
        if(authentication == null) throw new EntityNotFoundException("No token");
        if(authentication instanceof JwtAuthenticationToken) {
            principal = (String) ((JwtAuthenticationToken) authentication).getTokenAttributes().get("username");
        }
        if(principal == null) throw new EntityNotFoundException("No token");
        return principal;
    }
}
