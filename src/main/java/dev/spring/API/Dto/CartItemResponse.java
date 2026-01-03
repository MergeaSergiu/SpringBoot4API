package dev.spring.API.Dto;

import dev.spring.API.model.CartItem;

public record CartItemResponse(
        Long productId,
        String productName,
        int quantity
) {

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity()
        );
    }
}
