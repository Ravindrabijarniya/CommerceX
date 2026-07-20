package com.ravindra.commercex.catalog.constants;


import java.util.Set;


public final class CatalogSortFields {


    private CatalogSortFields(){

    }


    public static final Set<String> ALLOWED_FIELDS =
        Set.of(
            "price",
            "name",
            "createdAt"
        );

}
