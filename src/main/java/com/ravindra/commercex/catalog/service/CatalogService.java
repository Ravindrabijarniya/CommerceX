package com.ravindra.commercex.catalog.service;


import com.ravindra.commercex.catalog.dto.ProductCardResponse;
import com.ravindra.commercex.catalog.dto.ProductDetailsResponse;
import com.ravindra.commercex.catalog.mapper.CatalogMapper;
import com.ravindra.commercex.catalog.validator.CatalogSortValidator;
import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.category.entity.CategoryStatus;
import com.ravindra.commercex.category.exception.CategoryNotFoundException;
import com.ravindra.commercex.category.repository.CategoryRepository;
import com.ravindra.commercex.product.exception.ProductNotFoundException;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.enums.ProductStatus;
import com.ravindra.commercex.product.repository.ProductRepository;
import com.ravindra.commercex.catalog.dto.ProductFilterRequest;
import com.ravindra.commercex.catalog.specification.ProductCatalogSpecification;

import org.springframework.data.jpa.domain.Specification;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatalogService {


    private final ProductRepository productRepository;

    private final CatalogMapper catalogMapper;

    private final CategoryRepository categoryRepository;

    private final CatalogSortValidator catalogSortValidator;



    public Page<ProductCardResponse> getProducts(
        Pageable pageable
    ){

        validateSorting(pageable);


        return productRepository
            .findAllByStatus(
                ProductStatus.ACTIVE,
                pageable
            )
            .map(catalogMapper::toProductCard);

    }

    public ProductDetailsResponse getProductBySlug(String slug){


        Product product = productRepository
            .findBySlug(slug)
            .orElseThrow(
                () -> new ProductNotFoundException(
                    "Product not found with slug: " + slug
                )
            );


        return catalogMapper.toProductDetails(product);

    }

    public Page<ProductCardResponse> getFeaturedProducts(
        Pageable pageable
    ){

        return productRepository
            .findAllByFeaturedTrueAndStatus(
                ProductStatus.ACTIVE,
                pageable
            )
            .map(catalogMapper::toProductCard);

    }

    public List<CategoryResponse> getCategories(){


        return categoryRepository
            .findByParentIsNullAndStatus(
                CategoryStatus.ACTIVE
            )
            .stream()
            .map(catalogMapper::toCategoryResponse)
            .toList();

    }

    public Page<ProductCardResponse> getProductsByCategory(
        String slug,
        Pageable pageable
    ){

        Category category =
            categoryRepository
                .findBySlugAndStatus(
                    slug,
                    CategoryStatus.ACTIVE
                )
                .orElseThrow(
                    () -> new CategoryNotFoundException(
                        "Category not found: " + slug
                    )
                );


        return productRepository
            .findAllByCategoryAndStatus(
                category,
                ProductStatus.ACTIVE,
                pageable
            )
            .map(catalogMapper::toProductCard);

    }

    public Page<ProductCardResponse> searchProducts(
        String keyword,
        Pageable pageable
    ){

        return productRepository
            .searchProducts(
                ProductStatus.ACTIVE,
                keyword,
                pageable
            )
            .map(catalogMapper::toProductCard);

    }

    private void validateSorting(Pageable pageable){


        pageable.getSort()
            .forEach(order ->
                catalogSortValidator.validate(
                    order.getProperty()
                )
            );

    }

    public Page<ProductCardResponse> filterProducts(
        ProductFilterRequest filter,
        Pageable pageable
    ){

        Specification<Product> specification =
            ProductCatalogSpecification.filter(
                ProductStatus.ACTIVE,
                filter.minPrice(),
                filter.maxPrice(),
                filter.featured()
            );


        return productRepository
            .findAll(
                specification,
                pageable
            )
            .map(catalogMapper::toProductCard);

    }



}
