package dev.spring.API.repository;

import dev.spring.API.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c from Cart c where c.username = :username")
    Optional<Cart> findCartByUsername(String username);

    @Modifying
    @Query("DELETE FROM Cart where username = :username")
    void deleteByUsername(String username);
}
