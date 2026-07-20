package com.ravindra.commercex.catalog.controller;


import com.ravindra.commercex.catalog.dto.ProductCardResponse;
import com.ravindra.commercex.catalog.dto.ProductDetailsResponse;
import com.ravindra.commercex.catalog.dto.ProductFilterRequest;
import com.ravindra.commercex.catalog.service.CatalogService;

import com.ravindra.commercex.category.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {


    private final CatalogService catalogService;



    @GetMapping("/products")
    public ResponseEntity<Page<ProductCardResponse>> getProducts(
        ProductFilterRequest filter,
        Pageable pageable
    ){

        return ResponseEntity.ok(
            catalogService.filterProducts(
                filter,
                pageable
            )
        );

    }

    @GetMapping("/products/{slug}")
    public ResponseEntity<ProductDetailsResponse> getProductDetails(
        @PathVariable String slug
    ){

        return ResponseEntity.ok(
            catalogService.getProductBySlug(slug)
        );

    }

    @GetMapping("/products/featured")
    public ResponseEntity<Page<ProductCardResponse>> getFeaturedProducts(
        Pageable pageable
    ){

        return ResponseEntity.ok(
            catalogService.getFeaturedProducts(pageable)
        );

    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories(){

        return ResponseEntity.ok(
            catalogService.getCategories()
        );

    }

    @GetMapping("/categories/{slug}/products")
    public ResponseEntity<Page<ProductCardResponse>>
    getProductsByCategory(
        @PathVariable String slug,
        Pageable pageable
    ){

        return ResponseEntity.ok(
            catalogService.getProductsByCategory(
                slug,
                pageable
            )
        );
    }

    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductCardResponse>> searchProducts(
        @RequestParam String q,
        Pageable pageable
    ){

        return ResponseEntity.ok(
            catalogService.searchProducts(
                q,
                pageable
            )
        );

    }

}
