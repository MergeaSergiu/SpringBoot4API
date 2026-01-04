package dev.spring.API.helper;

import dev.spring.API.Dto.CartItemResponse;
import dev.spring.API.Dto.CartResponse;
import dev.spring.API.model.Cart;
import dev.spring.API.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public  CartResponse from(Cart cart){
        return new CartResponse(
                cart.getUsername(),
                cart.getItems().stream()
                        .map(this::from)
                        .toList()
        );
    }

    public CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity()
        );
    }


}
