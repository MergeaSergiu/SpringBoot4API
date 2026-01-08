package dev.spring.API.Dto;

import dev.spring.API.model.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        String username,
        List<CartItemResponse> cartItemList,
        BigDecimal total

) {
}
