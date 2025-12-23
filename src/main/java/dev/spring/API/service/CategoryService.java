package dev.spring.API.service;

import dev.spring.API.Dto.CategoryRequest;
import dev.spring.API.model.Category;
import dev.spring.API.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        logger.info("Creating category: {}", category.getName());
        return categoryRepository.save(category).getId();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
