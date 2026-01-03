package dev.spring.API.Dto;

import dev.spring.API.model.Cart;

import java.util.List;

public record CartResponse(
        String username,
        List<CartItemResponse> cartItemList
) {
}
