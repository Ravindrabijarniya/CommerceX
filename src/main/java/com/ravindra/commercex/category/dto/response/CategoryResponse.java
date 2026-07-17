package com.ravindra.commercex.category.dto.response;

public record CategoryResponse(

    Long id,

    String name,

    String description,

    String slug,

    String status,

    Integer sortOrder,

    Long parentId,

    String parentName

) {
}
