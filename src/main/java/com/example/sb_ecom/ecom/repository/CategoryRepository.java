package com.example.sb_ecom.ecom.repository;

import com.example.sb_ecom.ecom.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Category findByCategoryId(Long categoryId);

    Category findByCategoryName(String categoryName);
}
