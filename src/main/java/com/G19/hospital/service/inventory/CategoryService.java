package com.G19.hospital.service.inventory;

import com.G19.hospital.DTO.inventory.CategoryDto;
import com.G19.hospital.model.inventory.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    Category updateCategory(Long id, CategoryDto categoryDto);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
}
