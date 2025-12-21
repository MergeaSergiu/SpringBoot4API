package dev.spring.API.service;

import dev.spring.API.Dto.ProductRequest;
import dev.spring.API.model.Category;
import dev.spring.API.model.Product;
import dev.spring.API.repository.CategoryRepository;
import dev.spring.API.repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Long createProduct(ProductRequest productRequest){

        Optional<Category> category = categoryRepository.findById(productRequest.categoryId());
        if(category.isEmpty()) throw new RuntimeException("Category not found");
        Product newProduct = new Product();
        newProduct.setName(productRequest.name());
        newProduct.setPrice(productRequest.price());
        newProduct.setStock(productRequest.stock());
        newProduct.setDescription(productRequest.description());
        newProduct.setImageURL(productRequest.imageURL());
        newProduct.setCategory(category.get());
        logger.info("Creating product:{}, {}, {}", newProduct.getName(), newProduct.getPrice(), newProduct.getStock());

        return productRepository.save(newProduct).getId();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
