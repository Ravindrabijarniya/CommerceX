package com.ravindra.commercex.category.dto.request;

import com.ravindra.commercex.category.entity.CategoryStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeCategoryStatusRequest(

    @NotNull
    CategoryStatus status,

    Long version

) {
}
