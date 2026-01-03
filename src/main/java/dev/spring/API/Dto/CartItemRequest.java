package dev.spring.API.Dto;

public record CartItemRequest(

        Long productId,
        int quantity
) {
}
