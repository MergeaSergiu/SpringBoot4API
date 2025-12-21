package dev.spring.API.service;

import dev.spring.API.Dto.CategoryRequest;
import dev.spring.API.model.Category;
import dev.spring.API.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Long createProduct(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        return categoryRepository.save(category).getId();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
