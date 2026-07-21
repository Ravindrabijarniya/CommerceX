package com.ravindra.commercex.product.validation;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.exception.CategoryNotFoundException;
import com.ravindra.commercex.category.repository.CategoryRepository;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.exception.ProductAlreadyExistsException;
import com.ravindra.commercex.product.exception.ProductNotFoundException;
import com.ravindra.commercex.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductBusinessValidator {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product validateProductExists(Long productId) {

        return productRepository
            .findDetailedById(productId)
            .orElseThrow(() ->
                new ProductNotFoundException(productId));
    }

    public void validateUniqueSlug(String slug) {

        if (productRepository.existsBySlug(slug)) {
            throw new ProductAlreadyExistsException(
                "Product slug already exists."
            );
        }
    }

    public void validateUniqueSlug(String slug, Long productId) {

        if (productRepository.existsBySlugAndIdNot(slug, productId)) {
            throw new ProductAlreadyExistsException(
                "Product slug already exists.");
        }
    }

    public void validateUniqueSku(String sku, Long productId) {

        if (productRepository.existsBySkuAndIdNot(sku, productId)) {
            throw new ProductAlreadyExistsException(
                "Product SKU already exists.");
        }
    }

    public void validateUniqueSku(String sku) {

        if (productRepository.existsBySku(sku)) {
            throw new ProductAlreadyExistsException(
                "Product SKU already exists."
            );
        }
    }

    public Category validateCategoryExists(Long categoryId) {

        return categoryRepository.findById(categoryId)
            .orElseThrow(() ->
                new CategoryNotFoundException(categoryId));
    }


}
