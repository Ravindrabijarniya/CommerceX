package com.ravindra.commercex.product.mapper;

import com.ravindra.commercex.product.dto.request.CreateProductRequest;
import com.ravindra.commercex.product.dto.request.UpdateProductRequest;
import com.ravindra.commercex.product.dto.response.ProductResponse;
import com.ravindra.commercex.product.dto.response.ProductSummaryResponse;
import com.ravindra.commercex.product.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    ProductSummaryResponse toSummaryResponse(Product product);

    List<ProductSummaryResponse> toSummaryResponseList(List<Product> products);
}
