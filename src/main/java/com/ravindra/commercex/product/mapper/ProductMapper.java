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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product toEntity(CreateProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(UpdateProductRequest request,
                      @MappingTarget Product product);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    ProductSummaryResponse toSummaryResponse(Product product);

    List<ProductSummaryResponse> toSummaryResponseList(List<Product> products);
}
