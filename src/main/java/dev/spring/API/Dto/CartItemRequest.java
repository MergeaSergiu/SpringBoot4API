package dev.spring.API.Dto;

public record CartItemRequest(
        Long cartItemId,
        int quantity
) {
}
