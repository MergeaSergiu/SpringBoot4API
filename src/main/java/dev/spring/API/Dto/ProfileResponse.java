package dev.spring.API.Dto;

public record ProfileResponse(
    String username
) {

    public ProfileResponse() {
        this("");
    }
}
