package com.ravindra.commercex.product.controller;

import com.ravindra.commercex.product.dto.request.CreateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductStatusRequest;
import com.ravindra.commercex.product.dto.response.ProductResponse;
import com.ravindra.commercex.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(
        @Valid @RequestBody CreateProductRequest request) {

        return productService.createProduct(request);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateProductRequest request) {

        return productService.updateProduct(productId, request);
    }

    @PatchMapping("/{productId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductStatus(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateProductStatusRequest request) {

        productService.updateProductStatus(productId, request);
    }
}
