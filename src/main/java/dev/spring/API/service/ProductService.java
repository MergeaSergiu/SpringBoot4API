package dev.spring.API.service;

import dev.spring.API.Dto.ProductRequest;
import dev.spring.API.Dto.ProductResponse;
import dev.spring.API.component.RabbitMQProducer;
import dev.spring.API.model.Category;
import dev.spring.API.model.Product;
import dev.spring.API.repository.CategoryRepository;
import dev.spring.API.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RabbitMQProducer rabbitMQProducer;
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository, RabbitMQProducer rabbitMQProducer) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public Long createProduct(ProductRequest productRequest){

        Optional<Category> category = categoryRepository.findById(productRequest.categoryId());
        if(category.isEmpty()) throw new EntityNotFoundException("Category not found");
        Product newProduct = new Product();
        newProduct.setName(productRequest.name());
        newProduct.setPrice(productRequest.price());
        newProduct.setStock(productRequest.stock());
        newProduct.setDescription(productRequest.description());
        newProduct.setImageURL(productRequest.imageURL());
        newProduct.setCategory(category.get());
        logger.info("Creating product:{}, {}, {}", newProduct.getName(), newProduct.getPrice(), newProduct.getStock());

        Long Id = productRepository.save(newProduct).getId();

        rabbitMQProducer.sendMessage(newProduct);
        return Id;
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return
                productRepository.findAll(pageable).map(this::toDto);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageURL(),
                product.getCreatedDate(),
                product.getUpdatedDate(),
                product.getCategory().getId()
        );
    }

    public Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        Page<Product> products = productRepository.findAllByCategory(category, pageable);
        return products.map(this::toDto);
    }
}
