package com.example.sb_ecom.ecom.controller;

import com.example.sb_ecom.ecom.Config.AppConstant;
import com.example.sb_ecom.ecom.model.Category;
import com.example.sb_ecom.ecom.payload.CategoryDTO;
import com.example.sb_ecom.ecom.payload.CategoryResponse;
import com.example.sb_ecom.ecom.service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/public/categories")
    public CategoryResponse getCategoryList(
            @RequestParam(name = "PageNumber" , required = false , defaultValue = AppConstant.PAGE_NUMBER) Integer pageNumber ,
            @RequestParam(name = "PageSize" , required = false , defaultValue = AppConstant.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "SortBy" , required = false , defaultValue = AppConstant.SORT_BY) String sortBy,
            @RequestParam(name = "SortOrder" , required = false , defaultValue = AppConstant.SORT_ORDER) String sortOrder


            ){
        return categoryService.getCategoryList(pageNumber, pageSize, sortBy, sortOrder);
    }

    @PostMapping("/api/public/categories")
    public void addCategory(@Valid @RequestBody CategoryDTO categoryDTOObj){
        categoryService.addCategory(categoryDTOObj);
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId , @RequestBody CategoryDTO categoryDTOObj){
        return categoryService.updateCategory(categoryId, categoryDTOObj);
    }

    @DeleteMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("categoryId") Long categoryId){

            ResponseEntity<CategoryDTO> status = categoryService.deleteCategory(categoryId);
            return status;



        //return categoryService.deleteCategory(categoryId);
    }
}
