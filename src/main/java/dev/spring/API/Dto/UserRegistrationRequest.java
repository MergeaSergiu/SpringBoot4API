package dev.spring.API.Dto;

public record UserRegistrationRequest(
        String username,
        String password,
        String email,
        String givenName,
        String familyName
) {
}
