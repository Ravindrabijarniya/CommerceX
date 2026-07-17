package com.ravindra.commercex.product.repository;

import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySlug(String slug);

    boolean existsBySku(String sku);

    Optional<Product> findBySlug(String slug);

    Optional<Product> findByIdAndStatus(Long id, ProductStatus status);

    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);
}
