package com.ravindra.commercex.category.repository;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.entity.CategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository
    extends JpaRepository<Category, Long>,
    JpaSpecificationExecutor<Category> {

    Optional<Category> findBySlug(String slug);

    Optional<Category> findBySlugAndStatus(
        String slug,
        CategoryStatus status
    );

    boolean existsBySlug(String slug);

    boolean existsByParentAndName(
        Category parent,
        String name
    );

    boolean existsByParentAndNameAndIdNot(
        Category parent,
        String name,
        Long id
    );

    List<Category> findByParent(Category parent);

    List<Category> findByParentAndStatus(
        Category parent,
        CategoryStatus status
    );

    List<Category> findByParentIsNull();

    List<Category> findByParentIsNullAndStatus(
        CategoryStatus status
    );

    List<Category> findByStatus(
        CategoryStatus status
    );

    Page<Category> findByStatus(
        CategoryStatus status,
        Pageable pageable
    );

    Page<Category> findByNameContainingIgnoreCase(
        String keyword,
        Pageable pageable
    );

    boolean existsByParent(Category parent);

    long countByStatus(
        CategoryStatus status
    );

    @EntityGraph(attributePaths = "parent")
    Optional<Category> findWithParentById(Long id);

}
