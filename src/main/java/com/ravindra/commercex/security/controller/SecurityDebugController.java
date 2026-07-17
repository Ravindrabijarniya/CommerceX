package com.ravindra.commercex.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityDebugController {

    @GetMapping("/me")
    public Authentication me(Authentication authentication) {
        return authentication;
    }

}
