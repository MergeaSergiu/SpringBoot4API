package dev.spring.API.Dto;

import jakarta.validation.constraints.NotBlank;

public record GroupRequest(

        @NotBlank(message = "Name must not be Blank")
        String name,
        @NotBlank(message = "Description must not be Blank")
        String description,
        @NotBlank(message = "City must not be Blank")
        String city,
        @NotBlank(message = "Organizer must not be Blank")
        String organizer
) {
}
