package dev.spring.API.Dto;



public record CartItemResponse(
        Long productId,
        String productName,
        int quantity,
        Long cartItemId
) {


}
