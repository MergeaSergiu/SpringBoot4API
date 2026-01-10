package dev.spring.API.controller;

import dev.spring.API.Dto.CartItemRequest;
import dev.spring.API.Dto.CartResponse;
import dev.spring.API.helper.HelperUtils;
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
    public ResponseEntity<CartResponse> addItemInCart(@RequestParam Long productId,
                                                      Authentication authorization) {

        String username = helperUtils.extractUsername(authorization);
        CartResponse cartResponse = cartService.addItemToCart(productId, username);
        return ResponseEntity.ok(cartResponse);
    }

    @PutMapping
    public ResponseEntity<CartResponse> updateItemInCart(@RequestBody @Valid CartItemRequest cartItemRequest ,Authentication authorization) {
        String username = helperUtils.extractUsername(authorization);
        CartResponse cartResponse = cartService.updateItemInCart(cartItemRequest,username);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCart(Authentication authorization) {
        String username = helperUtils.extractUsername(authorization);
        cartService.deleteCart(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long productId, Authentication authorization) {
        String username = helperUtils.extractUsername(authorization);
        CartResponse cartResponse = cartService.removeItemFromCart(productId, username);
        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authorization) {
        String username = helperUtils.extractUsername(authorization);
        CartResponse cartResponse = cartService.getCart(username);
        return ResponseEntity.ok(cartResponse);
    }
}
