package com.ravindra.commercex.profile.dto;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;


public record ProfileResponse(

    Long id,

    String firstName,

    String lastName,

    String email,

    Set<String> roles,

    LocalDateTime createdAt

) {}
