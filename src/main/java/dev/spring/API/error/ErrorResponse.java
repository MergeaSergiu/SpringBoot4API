package dev.spring.API.error;

public record ErrorResponse(
        String message,
        int status
) {
}
