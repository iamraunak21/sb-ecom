package com.example.sb_ecom.ecom.service;


import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.payload.CategoryDTO;
import com.example.sb_ecom.ecom.payload.CategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getCategoryList(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    public void addCategory(CategoryDTO categoryDTOObj);

    public ResponseEntity<String> updateCategory(Long CategoryId, CategoryDTO categoryDTOObj);

    public ResponseEntity<CategoryDTO> deleteCategory(Long categoryId);
}
