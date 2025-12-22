package dev.spring.API.Dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Description must not be blank")
        String description,

        @NotBlank(message = "Price must not be blank")
        String price,

        @NotBlank(message = "Stock must not be blank")
        String stock,

        @NotBlank(message = "Image must not be blank")
        String imageURL,

        Long categoryId
) { }
