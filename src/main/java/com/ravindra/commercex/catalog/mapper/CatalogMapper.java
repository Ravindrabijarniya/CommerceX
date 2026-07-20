package com.ravindra.commercex.catalog.mapper;


import com.ravindra.commercex.catalog.dto.ProductCardResponse;
import com.ravindra.commercex.catalog.dto.ProductDetailsResponse;
import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CatalogMapper {


    @Mapping(
        target = "available",
        expression = "java(product.isAvailable())"
    )
    @Mapping(
        target = "categoryName",
        source = "category.name"
    )
    ProductCardResponse toProductCard(Product product);


    @Mapping(
        target = "available",
        expression = "java(product.isAvailable())"
    )
    @Mapping(
        target = "categoryName",
        source = "category.name"
    )
    ProductDetailsResponse toProductDetails(Product product);

    CategoryResponse toCategoryResponse(Category category);




}
