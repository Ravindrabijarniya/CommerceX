package com.ravindra.commercex.product.repository;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
    extends JpaRepository<Product,Long>,
    JpaSpecificationExecutor<Product> {


    boolean existsBySlug(String slug);

    boolean existsBySku(String sku);

    boolean existsBySlugAndIdNot(String slug, Long id);

    boolean existsBySkuAndIdNot(String sku, Long id);

    @EntityGraph(attributePaths = "category")
    Optional<Product> findBySlug(String slug);

    @EntityGraph(attributePaths = "category")
    Optional<Product> findDetailedById(Long id);

    List<Product> findByFeaturedTrueAndStatus(ProductStatus status);

    @EntityGraph(attributePaths = "category")
    Page<Product> findAllByStatus(
        ProductStatus status,
        Pageable pageable
    );

    @EntityGraph(attributePaths = "category")
    Page<Product> findAllByFeaturedTrueAndStatus(
        ProductStatus status,
        Pageable pageable
    );

    Page<Product> findAllByCategoryAndStatus(
        Category category,
        ProductStatus status,
        Pageable pageable
    );

    Page<Product> findByStatusAndNameContainingIgnoreCase(
        ProductStatus status,
        String keyword,
        Pageable pageable
    );

    @Query("""
        SELECT p
        FROM Product p
        WHERE p.status = :status
        AND (
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        """)
    Page<Product> searchProducts(
        @Param("status") ProductStatus status,
        @Param("keyword") String keyword,
        Pageable pageable
    );

}
