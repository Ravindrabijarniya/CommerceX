package com.ravindra.commercex.category.validation;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.entity.CategoryStatus;
import com.ravindra.commercex.category.exception.CategoryAlreadyExistsException;
import com.ravindra.commercex.category.exception.CategoryDeletionException;
import com.ravindra.commercex.category.exception.CategoryHierarchyException;
import com.ravindra.commercex.category.exception.CategoryStatusException;
import com.ravindra.commercex.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryBusinessValidator {

    private static final int MAX_DEPTH = 3;

    private final CategoryRepository repository;


    public void validateCreate(Category parent,
                               String name) {

        validateUniqueName(parent, name);
        validateParent(parent);
        validateHierarchyDepth(parent);
    }

    public void validateUpdate(Category category,
                               Category parent,
                               String name) {

        validateUniqueNameForUpdate(
            parent,
            name,
            category.getId()
        );

        validateParent(parent);
    }

    public void validateMove(Category category,
                             Category newParent) {

        validateParent(newParent);

        validateNoCircularReference(
            category,
            newParent
        );

        validateHierarchyDepth(newParent);
    }

    public void validateDelete(Category category) {

        validateHasNoChildren(category);

        /*
         * Future
         *
         * validateHasNoProducts(category);
         *
         */
    }

    public void validateStatusChange(Category category,
                                     CategoryStatus newStatus) {

        if (category.isDeleted()
            && newStatus == CategoryStatus.ACTIVE) {

            throw new CategoryStatusException(
                "Deleted category cannot be activated."
            );
        }

    }


    private void validateUniqueName(Category parent,
                                    String name) {

        if (repository.existsByParentAndName(parent, name)) {

            throw new CategoryAlreadyExistsException(
                "Category already exists with name: " + name
            );

        }

    }

    private void validateUniqueNameForUpdate(Category parent,
                                             String name,
                                             Long categoryId) {

        if (repository.existsByParentAndNameAndIdNot(
            parent,
            name,
            categoryId
        )) {

            throw new CategoryAlreadyExistsException(
                "Category already exists with name: " + name
            );

        }

    }

//    private void validateUniqueSlug(String slug) {
//
//        if (repository.existsBySlug(slug)) {
//
//            throw new CategoryAlreadyExistsException(
//                "Category slug already exists: " + slug
//            );
//
//        }
//
//    }

    private void validateParent(Category parent) {

        if (parent == null) {
            return;
        }

        if (parent.isDeleted()) {

            throw new CategoryHierarchyException(
                "Deleted category cannot be assigned as parent."
            );

        }

    }

    private void validateHasNoChildren(Category category) {

        if (repository.existsByParent(category)) {

            throw new CategoryDeletionException(
                "Category contains child categories."
            );

        }

    }

    private void validateNoCircularReference(Category category,
                                             Category newParent) {

        Category current = newParent;

        while (current != null) {

            if (current.equals(category)) {

                throw new CategoryHierarchyException(
                    "Circular hierarchy detected."
                );

            }

            current = current.getParent();
        }

    }

    private void validateHierarchyDepth(Category parent) {

        int depth = 1;

        Category current = parent;

        while (current != null) {

            depth++;

            if (depth > MAX_DEPTH) {

                throw new CategoryHierarchyException(
                    "Maximum category hierarchy depth exceeded."
                );

            }

            current = current.getParent();
        }

    }

}
