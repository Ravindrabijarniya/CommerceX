package com.ravindra.commercex.category.service;

import com.ravindra.commercex.category.dto.request.ChangeCategoryStatusRequest;
import com.ravindra.commercex.category.dto.request.CreateCategoryRequest;
import com.ravindra.commercex.category.dto.request.MoveCategoryRequest;
import com.ravindra.commercex.category.dto.request.UpdateCategoryRequest;
import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.dto.response.CategorySummaryResponse;
import com.ravindra.commercex.category.dto.response.CategoryTreeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(
        Long categoryId,
        UpdateCategoryRequest request
    );

    void moveCategory(
        Long categoryId,
        MoveCategoryRequest request
    );

    void changeStatus(
        Long categoryId,
        ChangeCategoryStatusRequest request
    );

    void deleteCategory(Long categoryId);

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse getCategoryBySlug(String slug);

    List<CategorySummaryResponse> getRootCategories();

    List<CategoryTreeResponse> getCategoryTree();

    Page<CategorySummaryResponse> searchCategories(
        String keyword,
        Pageable pageable
    );

}
