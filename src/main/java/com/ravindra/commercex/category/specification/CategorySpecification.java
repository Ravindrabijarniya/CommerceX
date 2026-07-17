package com.ravindra.commercex.category.specification;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.entity.CategoryStatus;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {

    private CategorySpecification() {
    }

    public static Specification<Category> hasName(String keyword) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }

            return cb.like(
                cb.lower(root.get("name")),
                "%" + keyword.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Category> hasStatus(CategoryStatus status) {

        return (root, query, cb) -> {

            if (status == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Category> hasParent(Long parentId) {

        return (root, query, cb) -> {

            if (parentId == null) {
                return cb.conjunction();
            }

            return cb.equal(
                root.get("parent").get("id"),
                parentId
            );
        };
    }

    public static Specification<Category> isRootCategory() {

        return (root, query, cb) ->
            cb.isNull(root.get("parent"));
    }

    public static Specification<Category> isActive() {

        return (root, query, cb) ->
            cb.equal(root.get("status"), CategoryStatus.ACTIVE);
    }

}
