package dev.spring.API.Dto;

import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String price,
        String stock,
        String imageURL,
        LocalDate createdDate,
        LocalDate updatedDate,
        Long categoryId
) {
}
