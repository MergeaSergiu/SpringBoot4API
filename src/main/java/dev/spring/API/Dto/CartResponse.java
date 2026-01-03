package dev.spring.API.Dto;

import dev.spring.API.model.Cart;

import java.util.List;

public record CartResponse(
        String username,
        List<CartItemResponse> cartItemList
) {

    public static CartResponse from(Cart cart){
        return new CartResponse(
                cart.getUsername(),
                cart.getItems().stream()
                        .map(CartItemResponse::from)
                        .toList()
        );
    }
}
