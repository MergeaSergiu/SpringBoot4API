package dev.spring.API.repository;


import dev.spring.API.model.Category;
import dev.spring.API.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @EntityGraph(attributePaths = "category")
//    @Query("SELECT p from Product p JOIN FETCH p.category WHERE p.category.id = :categoryId")
    Page<Product> findAllByCategory(Category category, Pageable pageable);
}
