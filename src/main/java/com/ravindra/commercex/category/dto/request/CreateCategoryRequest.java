package com.ravindra.commercex.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(

    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    String name,

    @Size(max = 500)
    String description,

    Long parentId,

    @PositiveOrZero
    Integer sortOrder

) {
}
