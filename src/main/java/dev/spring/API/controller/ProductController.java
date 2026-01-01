package dev.spring.API.controller;

import dev.spring.API.Dto.ProductRequest;
import dev.spring.API.Dto.ProductResponse;
import dev.spring.API.model.Product;
import dev.spring.API.service.ProductService;
import jakarta.validation.Valid;
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
     public ResponseEntity<List<ProductResponse>> getAllProducts(){
         return ResponseEntity.status(200).body(productService.getAllProducts());
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
    public ResponseEntity<List<ProductResponse>> getAllProductsByCategory(@PathVariable Long categoryId){
         List<ProductResponse> productResponses = productService.getProductsByCategory(categoryId);
         return ResponseEntity.ok(productResponses);
    }

}
