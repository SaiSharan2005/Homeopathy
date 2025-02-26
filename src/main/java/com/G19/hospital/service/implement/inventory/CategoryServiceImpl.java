package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.CategoryDto;
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Category;
import com.G19.hospital.repository.inventory.CategoryRepository;
import com.G19.hospital.service.inventory.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        try {
            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            // InventoryItems will be empty on creation.
            return categoryRepository.save(category);
        } catch (Exception ex) {
            log.error("Error creating category: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to create category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Category not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            existingCategory.setName(categoryDto.getName());
            existingCategory.setDescription(categoryDto.getDescription());
            return categoryRepository.save(existingCategory);
        } catch (Exception ex) {
            log.error("Error updating category: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to update category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Category not found with id: " + id, HttpStatus.NOT_FOUND));
        try {
            categoryRepository.delete(existingCategory);
        } catch (Exception ex) {
            log.error("Error deleting category: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception ex) {
            log.error("Error retrieving categories: {}", ex.getMessage(), ex);
            throw new CustomSecurityException("Failed to retrieve categories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Category not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}
