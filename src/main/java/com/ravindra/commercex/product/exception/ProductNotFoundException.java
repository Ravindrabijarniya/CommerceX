package com.ravindra.commercex.product.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }

    public ProductNotFoundException(String slug) {
        super("Product not found with slug: " + slug);
    }
}
