package com.ravindra.commercex.category.service.impl;

import com.ravindra.commercex.category.dto.request.ChangeCategoryStatusRequest;
import com.ravindra.commercex.category.dto.request.CreateCategoryRequest;
import com.ravindra.commercex.category.dto.request.MoveCategoryRequest;
import com.ravindra.commercex.category.dto.request.UpdateCategoryRequest;
import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.dto.response.CategorySummaryResponse;
import com.ravindra.commercex.category.dto.response.CategoryTreeResponse;
import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.entity.CategoryStatus;
import com.ravindra.commercex.category.exception.CategoryNotFoundException;
import com.ravindra.commercex.category.mapper.CategoryMapper;
import com.ravindra.commercex.category.repository.CategoryRepository;
import com.ravindra.commercex.category.service.CategoryService;
import com.ravindra.commercex.category.validation.CategoryBusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryBusinessValidator validator;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        Category parent = null;

        if (request.parentId() != null) {
            parent = getCategoryEntity(request.parentId());
        }

        validator.validateCreate(
            parent,
            request.name()
        );

        Category category = Category.builder()
            .name(request.name())
            .description(request.description())
            .slug(generateSlug(request.name()))
            .sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
            .status(CategoryStatus.ACTIVE)
            .build();

        if (parent != null) {
            parent.addChild(category);
        }

        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId,
                                           UpdateCategoryRequest request) {

        throw new UnsupportedOperationException("Coming soon");
    }

    @Override
    public void moveCategory(Long categoryId,
                                         MoveCategoryRequest request) {

        throw new UnsupportedOperationException("Coming soon");
    }

    @Override
    public void changeStatus(Long categoryId,
                                         ChangeCategoryStatusRequest request) {

        throw new UnsupportedOperationException("Coming soon");
    }

    @Override
    public void deleteCategory(Long categoryId) {

        throw new UnsupportedOperationException("Coming soon");
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {

        Category category = getCategoryEntity(categoryId);

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug)
            .orElseThrow(() ->
                new CategoryNotFoundException(
                    "Category not found with slug: " + slug));

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorySummaryResponse> getRootCategories() {

        return categoryMapper.toSummaryList(
            categoryRepository.findByParentIsNullAndStatus(CategoryStatus.ACTIVE)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeResponse> getCategoryTree() {

        return categoryMapper.toTreeList(
            categoryRepository.findByParentIsNullAndStatus(CategoryStatus.ACTIVE)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorySummaryResponse> searchCategories(String keyword,
                                                          Pageable pageable) {

        return categoryRepository
            .findByNameContainingIgnoreCase(keyword,  pageable)
            .map(categoryMapper::toSummary);
    }

    private Category getCategoryEntity(Long id) {

        return categoryRepository.findById(id)
            .orElseThrow(() ->
                new CategoryNotFoundException(
                    "Category not found with id: " + id));
    }

    private String generateSlug(String name) {

        return name
            .trim()
            .toLowerCase()
            .replace(" ", "-");
    }

}
