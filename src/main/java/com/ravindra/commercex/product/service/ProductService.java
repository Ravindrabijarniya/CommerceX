package com.ravindra.commercex.product.service;

import com.ravindra.commercex.product.dto.request.CreateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductStatusRequest;
import com.ravindra.commercex.product.dto.response.ProductResponse;
import com.ravindra.commercex.product.dto.response.ProductSummaryResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long productId,
                                  UpdateProductRequest request);

    void updateProductStatus(Long productId,
                             UpdateProductStatusRequest request);

    ProductResponse getProduct(Long productId);

    ProductResponse getProductBySlug(String slug);

    List<ProductSummaryResponse> getFeaturedProducts();
}
