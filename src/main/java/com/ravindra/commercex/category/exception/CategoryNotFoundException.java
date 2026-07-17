package com.ravindra.commercex.category.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with id: " + categoryId);
    }

    public CategoryNotFoundException(String slug) {
        super("Category not found with slug: " + slug);
    }

}
