package dev.spring.API.service;

import dev.spring.API.Dto.CartItemRequest;
import dev.spring.API.Dto.CartResponse;
import dev.spring.API.helper.CartMapper;
import dev.spring.API.model.Cart;
import dev.spring.API.model.CartItem;
import dev.spring.API.model.Product;
import dev.spring.API.repository.CartItemRepository;
import dev.spring.API.repository.CartRepository;
import dev.spring.API.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional
    public CartResponse addItemToCart(CartItemRequest cartItemRequest, String username ) {
        if(cartItemRequest.quantity() <= 0) throw new IllegalArgumentException("Quantity must be positive");

        Product product = productRepository.findById(cartItemRequest.productId()).orElseThrow(()->new EntityNotFoundException("Product not found"));

        Cart cart = cartRepository.findCartByUsername(username).orElseGet(
                () -> {
                    Cart newCart = new Cart();
                    newCart.setUsername(username);
                    return cartRepository.save(newCart);
                }
        );
        CartItem cartItem = cartItemRepository.findCartItemByProductAndCart(cart.getId(),  product.getId());
        if(cartItem == null) {
           cartItem = CartItem.builder()
                   .product(product)
                   .quantity(cartItemRequest.quantity())
                   .cart(cart)
                   .build();
           cartItemRepository.save(cartItem);
           cart.getItems().add(cartItem);

       }else{
           cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.quantity());
       }

       return cartMapper.from(cart);

    }

    @Transactional
    public void deleteCart(String username) {
        Cart cart = cartRepository.findCartByUsername(username).orElseThrow(()->new EntityNotFoundException("No cart found for this user"));
        cartRepository.delete(cart);
    }


    @Transactional
    public CartResponse removeItemFromCart(Long productId, String username) {
        int deleted = cartItemRepository.deleteByUsernameAndProductId(username, productId);
        if(deleted == 0) throw new EntityNotFoundException("No cart found for this user");
        Cart cart = cartRepository.findCartByUsernameWithItems(username).orElseThrow(()->new EntityNotFoundException("No cart found for this user"));
        return cartMapper.from(cart);
    }

    public CartResponse getCart(String username) {
       Cart cart =  cartRepository.findCartByUsernameWithItems(username).orElseThrow( () -> new EntityNotFoundException("No cart found for this user"));
       return cartMapper.from(cart);

    }
}
