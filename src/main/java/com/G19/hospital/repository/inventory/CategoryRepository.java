package com.G19.hospital.repository.inventory;

import com.G19.hospital.model.inventory.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository already provides findById, so no additional custom method is required.
    // Optionally, you can define custom queries if needed.
    Optional<Category> findByName(String name);
}
