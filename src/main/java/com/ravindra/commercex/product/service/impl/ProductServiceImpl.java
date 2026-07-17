package com.ravindra.commercex.product.service.impl;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.product.dto.request.CreateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductStatusRequest;
import com.ravindra.commercex.product.dto.response.ProductResponse;
import com.ravindra.commercex.product.dto.response.ProductSummaryResponse;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.enums.ProductStatus;
import com.ravindra.commercex.product.exception.ProductNotFoundException;
import com.ravindra.commercex.product.mapper.ProductMapper;
import com.ravindra.commercex.product.repository.ProductRepository;
import com.ravindra.commercex.product.service.ProductService;
import com.ravindra.commercex.product.validation.ProductBusinessValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.validation.Validator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl
    implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    private final ProductBusinessValidator validator;


    @Override
    public ProductResponse createProduct(
        CreateProductRequest request) {

        validator.validateUniqueSlug(request.slug());

        validator.validateUniqueSku(request.sku());

        Category category =
            validator.validateCategoryExists(
                request.categoryId());

        Product product =
            mapper.toEntity(request);

        product.assignCategory(category);

        Product saved =
            repository.save(product);

        return mapper.toResponse(saved);
    }

    @Override
    public ProductResponse updateProduct(
        Long productId,
        UpdateProductRequest request) {

        Product product =
            validator.validateProductExists(productId);

        validator.validateUniqueSlug(
            request.slug(),
            productId);

        validator.validateUniqueSku(
            request.sku(),
            productId);

        Category category =
            validator.validateCategoryExists(
                request.categoryId());

        mapper.updateEntity(request, product);

        product.assignCategory(category);

        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {

        return mapper.toResponse(
            validator.validateProductExists(id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String slug) {

        Product product =
            repository.findBySlug(slug)
                .orElseThrow(() ->
                    new ProductNotFoundException(slug));

        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getFeaturedProducts() {

        return mapper.toSummaryResponseList(
            repository.findByFeaturedTrueAndStatus(
                ProductStatus.ACTIVE
            )
        );
    }

    @Override
    public void updateProductStatus(
        Long id,
        UpdateProductStatusRequest request) {

        Product product =
            validator.validateProductExists(id);

        product.changeStatus(request.status());
    }
}
