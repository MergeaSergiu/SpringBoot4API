package dev.spring.API.repository;


import dev.spring.API.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p from Product p JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findAllByCategory(@Param("categoryId") Long categoryId);
}
