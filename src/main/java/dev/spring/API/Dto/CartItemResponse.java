package dev.spring.API.Dto;

import dev.spring.API.model.CartItem;

public record CartItemResponse(
        Long productId,
        String productName,
        int quantity
) {


}
