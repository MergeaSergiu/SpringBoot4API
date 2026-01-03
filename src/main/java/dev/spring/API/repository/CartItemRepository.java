package dev.spring.API.repository;

import dev.spring.API.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT cartItem FROM CartItem cartItem WHERE cartItem.cart.id = :cartId AND cartItem.product.id = :productId")
    CartItem findCartItemByProductAndCart(Long cartId, Long productId);

    @Modifying
    @Query("DELETE from CartItem cartitem where cartitem.cart.id = :cartId")
    void deleteByCart(Long cartId);

}
