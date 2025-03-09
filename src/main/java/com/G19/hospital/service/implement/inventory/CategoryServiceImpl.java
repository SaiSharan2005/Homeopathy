package com.G19.hospital.service.implement.inventory;

import com.G19.hospital.DTO.inventory.CategoryDto; // adjust package name if necessary
import com.G19.hospital.exceptions.security.CustomSecurityException;
import com.G19.hospital.model.inventory.Category;
import com.G19.hospital.model.User;
import com.G19.hospital.repository.inventory.CategoryRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.service.inventory.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;  // Declared userRepository

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        try {
            // Retrieve the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomSecurityException("User not found", HttpStatus.NOT_FOUND));

            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            // Set createdAt and updatedAt to current time
            LocalDateTime now = LocalDateTime.now();
            category.setCreatedAt(now);
            category.setUpdatedAt(now);
            // Set createdBy to the current logged-in user
            category.setCreatedBy(user);
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
            // Update the updatedAt field to current time
            existingCategory.setUpdatedAt(LocalDateTime.now());
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
