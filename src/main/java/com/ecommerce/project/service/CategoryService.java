package com.ecommerce.project.service;


import com.ecommerce.project.dto.CategoryRequest;
import com.ecommerce.project.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryRequest createCategory(CategoryRequest categoryRequest);

    CategoryRequest deleteCategoryById(Long categoryId);

    CategoryRequest updateCategory(Long categoryId, CategoryRequest categoryRequest);
}
