package dev.spring.API.Dto;

public record UserLoginRequest(
        String username,
        String password
) {
}
