package dev.spring.API.Dto;

public record GroupRequest(
        String name,
        String description,
        String city,
        String organizer
) {
}
