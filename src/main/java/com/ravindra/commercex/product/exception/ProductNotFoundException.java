package com.ravindra.commercex.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    }

    public ProductNotFoundException(String slug) {
        super("Product not found with slug: " + slug);
    }

}
