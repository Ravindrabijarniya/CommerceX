package com.ravindra.commercex.category.mapper;

import com.ravindra.commercex.category.dto.response.CategoryResponse;
import com.ravindra.commercex.category.dto.response.CategorySummaryResponse;
import com.ravindra.commercex.category.dto.response.CategoryTreeResponse;
import com.ravindra.commercex.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "status", source = "status")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    CategoryResponse toResponse(Category category);

    CategorySummaryResponse toSummary(Category category);

    CategoryTreeResponse toTree(Category category);

    List<CategorySummaryResponse> toSummaryList(List<Category> categories);

    List<CategoryTreeResponse> toTreeList(List<Category> categories);

}
