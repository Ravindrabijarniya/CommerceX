package com.ravindra.commercex.catalog.specification;


import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.enums.ProductStatus;

import org.springframework.data.jpa.domain.Specification;


import java.math.BigDecimal;


public final class ProductCatalogSpecification {


    private ProductCatalogSpecification(){}


    public static Specification<Product> filter(
        ProductStatus status,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean featured
    ){

        return (root, query, cb) -> {


            var predicate =
                cb.equal(
                    root.get("status"),
                    status
                );


            if(minPrice != null){

                predicate =
                    cb.and(
                        predicate,
                        cb.greaterThanOrEqualTo(
                            root.get("price"),
                            minPrice
                        )
                    );
            }


            if(maxPrice != null){

                predicate =
                    cb.and(
                        predicate,
                        cb.lessThanOrEqualTo(
                            root.get("price"),
                            maxPrice
                        )
                    );
            }


            if(featured != null){

                predicate =
                    cb.and(
                        predicate,
                        cb.equal(
                            root.get("featured"),
                            featured
                        )
                    );
            }


            return predicate;

        };

    }

}
