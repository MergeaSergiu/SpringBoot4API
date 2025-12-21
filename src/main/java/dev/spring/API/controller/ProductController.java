package dev.spring.API.controller;

import dev.spring.API.Dto.ProductRequest;
import dev.spring.API.model.Product;
import dev.spring.API.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    public final ProductService productService;

     public ProductController(ProductService productService) {
         this.productService = productService;
     }

     @GetMapping
     public ResponseEntity<List<Product>> getAllProducts(){
         return ResponseEntity.status(200).body(productService.getAllProducts());
     }

    @PostMapping
    public ResponseEntity<Long> createProduct(ProductRequest productRequest){
         return ResponseEntity.accepted().body(productService.createProduct(productRequest));
    }

}
