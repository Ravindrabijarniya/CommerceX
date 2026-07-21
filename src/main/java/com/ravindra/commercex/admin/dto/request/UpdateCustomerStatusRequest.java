package com.ravindra.commercex.admin.dto.request;


import jakarta.validation.constraints.NotNull;


public record UpdateCustomerStatusRequest(

    @NotNull
    Boolean enabled

) {}
