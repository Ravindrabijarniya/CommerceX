package com.ravindra.commercex.admin.dto.response;


import java.time.LocalDateTime;
import java.util.Set;


public record AdminCustomerResponse(

    Long id,

    String firstName,

    String lastName,

    String email,

    boolean enabled,

    boolean accountLocked,

    Set<String> roles,

    LocalDateTime createdAt

) {}
