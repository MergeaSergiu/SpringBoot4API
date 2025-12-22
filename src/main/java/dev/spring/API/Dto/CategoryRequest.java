package dev.spring.API.Dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(

        @NotBlank(message = "Name must not be blank")
        String name
) {
}
