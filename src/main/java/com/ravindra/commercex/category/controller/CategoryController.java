package com.ravindra.commercex.category.controller;

import com.ravindra.commercex.category.dto.request.CreateCategoryRequest;
import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.dto.response.CategorySummaryResponse;
import com.ravindra.commercex.category.dto.response.CategoryTreeResponse;
import com.ravindra.commercex.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
        @Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
        @PathVariable Long id) {

        return ResponseEntity.ok(
            categoryService.getCategoryById(id)
        );
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryResponse> getCategoryBySlug(
        @PathVariable String slug) {

        return ResponseEntity.ok(
            categoryService.getCategoryBySlug(slug)
        );
    }

    @GetMapping("/roots")
    public ResponseEntity<List<CategorySummaryResponse>> getRootCategories() {

        return ResponseEntity.ok(
            categoryService.getRootCategories()
        );
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeResponse>> getCategoryTree() {

        return ResponseEntity.ok(
            categoryService.getCategoryTree()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategorySummaryResponse>> searchCategories(
        @RequestParam(defaultValue = "") String keyword,
        Pageable pageable) {

        return ResponseEntity.ok(
            categoryService.searchCategories(keyword, pageable)
        );
    }

}
