package com.ravindra.commercex.profile.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UpdateProfileRequest(

    @NotBlank
    @Size(max = 50)
    String firstName,


    @NotBlank
    @Size(max = 50)
    String lastName

) {}
