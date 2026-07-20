package com.ravindra.commercex.catalog.util;


import com.ravindra.commercex.catalog.dto.CatalogPageResponse;
import org.springframework.data.domain.Page;


public final class CatalogPageMapper {


    private CatalogPageMapper(){}


    public static <T> CatalogPageResponse<T> map(
        Page<T> page
    ){

        return new CatalogPageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }

}
