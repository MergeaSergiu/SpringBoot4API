package dev.spring.API.helper;

import dev.spring.API.Dto.CartItemResponse;
import dev.spring.API.Dto.CartResponse;
import dev.spring.API.model.Cart;
import dev.spring.API.model.CartItem;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartMapper {



    public  CartResponse from(Cart cart) {

        BigDecimal total = cart.getItems().stream()
                .map(item -> {
                    BigDecimal price = new BigDecimal(item.getProduct().getPrice());
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getUsername(),
                cart.getItems().stream()
                        .map(this::from)
                        .toList(),
                total
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
