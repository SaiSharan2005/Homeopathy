package com.G19.hospital.repository.inventory.core;

import org.springframework.data.jpa.repository.JpaRepository;

import com.G19.hospital.model.inventory.core.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository already provides findById, so no additional custom method is required.
    // Optionally, you can define custom queries if needed.
    Optional<Category> findByName(String name);
    Boolean existsByName(String name);

}
