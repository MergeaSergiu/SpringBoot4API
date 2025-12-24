package dev.spring.API.controller;

import dev.spring.API.Dto.CategoryRequest;
import dev.spring.API.model.Category;
import dev.spring.API.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        Long id = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(201).body(id);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.status(200).body(categories);
    }


}
