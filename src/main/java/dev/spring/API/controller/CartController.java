package dev.spring.API.controller;

import dev.spring.API.Dto.CartItemRequest;
import dev.spring.API.Dto.CartResponse;
import dev.spring.API.helper.HelperUtils;
import dev.spring.API.model.Cart;
import dev.spring.API.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
public class CartController {

    private final CartService cartService;
    private final HelperUtils helperUtils;

    public CartController(CartService cartService, HelperUtils helperUtils) {
        this.cartService = cartService;
        this.helperUtils = helperUtils;
    }

    @PostMapping
    public ResponseEntity<CartResponse> addItemInCart(@RequestBody @Valid CartItemRequest cartItemRequest,
                                                      Authentication authorization) {

        String username = helperUtils.extractUsername(authorization);
        Cart cart = cartService.addItemToCart(cartItemRequest, username);

        CartResponse cartResponse = CartResponse.from(cart);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCart(Authentication authorization) {
        String username = helperUtils.extractUsername(authorization);
        cartService.deleteCart(username);
        return ResponseEntity.noContent().build();
    }
}
