package dev.spring.API.controller;


import dev.spring.API.Dto.UserLoginRequest;
import dev.spring.API.Dto.UserRegistrationRequest;
import dev.spring.API.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        try {
            userService.registerUser(userRequest); // Register user with Cognito
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userRequest) {
        try {
            Map<String, String> tokens = userService.loginUser(userRequest); // Login user via Cognito
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        String principal = null;
        if(authentication == null) throw new EntityNotFoundException("No token");
        if(authentication instanceof JwtAuthenticationToken) {
            principal = (String) ((JwtAuthenticationToken) authentication).getTokenAttributes().get("username");
        }
        if(principal == null) throw new EntityNotFoundException("No token");
        return ResponseEntity.ok(principal);
    }

}
