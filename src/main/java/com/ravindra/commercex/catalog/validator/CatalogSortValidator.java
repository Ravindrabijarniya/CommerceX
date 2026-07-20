package com.ravindra.commercex.catalog.validator;


import com.ravindra.commercex.catalog.constants.CatalogSortFields;
import com.ravindra.commercex.catalog.exception.InvalidCatalogSortException;
import com.ravindra.commercex.common.exception.InvalidOperationException;

import org.springframework.stereotype.Component;


@Component
public class CatalogSortValidator {


    public void validate(String field){


        if(!CatalogSortFields.ALLOWED_FIELDS.contains(field)){

            throw new InvalidCatalogSortException(
                "Invalid sort field: " + field
            );

        }

    }

}
