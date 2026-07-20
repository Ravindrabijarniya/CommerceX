package com.ravindra.commercex.catalog.dto;


import java.util.List;


public record CatalogPageResponse<T>(

    List<T> content,

    int page,

    int size,

    long totalElements,

    int totalPages

) {

}
