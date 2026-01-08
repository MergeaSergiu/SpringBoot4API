package dev.spring.API.controller;

import dev.spring.API.Dto.ProductRequest;
import dev.spring.API.Dto.ProductResponse;
import dev.spring.API.model.Product;
import dev.spring.API.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    public final ProductService productService;

     public ProductController(ProductService productService) {
         this.productService = productService;
     }

     @GetMapping
     public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size){
         Pageable pageable = PageRequest.of(page,size);
         Page<ProductResponse> products = productService.getAllProducts(pageable);
         return ResponseEntity.status(200).body(products);
     }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest productRequest){
         return ResponseEntity.accepted().body(productService.createProduct(productRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id){
         productService.deleteProduct(id);
         return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Page<ProductResponse>> getAllProductsByCategory(@PathVariable Long categoryId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size){
         Pageable pageable = PageRequest.of(page,size);
         Page<ProductResponse> productResponses = productService.getProductsByCategory(categoryId,pageable);
         return ResponseEntity.ok(productResponses);
    }

}
