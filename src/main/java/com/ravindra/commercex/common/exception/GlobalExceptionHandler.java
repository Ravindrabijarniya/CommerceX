package com.ravindra.commercex.common.exception;

import com.ravindra.commercex.auth.exception.*;
import com.ravindra.commercex.category.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * ==========================
     * Validation Exceptions
     * ==========================
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
        MethodArgumentNotValidException ex,
        HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        return buildResponse(
            HttpStatus.BAD_REQUEST,
            "Validation Failed",
            request,
            errors
        );
    }

    /*
     * ==========================
     * Authentication Exceptions
     * ==========================
     */

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(
        EmailAlreadyExistsException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleRoleNotFound(
        RoleNotFoundException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
        BadCredentialsException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.UNAUTHORIZED,
            "Invalid email or password",
            request
        );
    }

    @ExceptionHandler({
        InvalidRefreshTokenException.class,
        RefreshTokenExpiredException.class,
        RefreshTokenRevokedException.class
    })
    public ResponseEntity<ApiErrorResponse> handleRefreshTokenExceptions(
        RuntimeException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            request
        );
    }

    /*
     * ==========================
     * Category Exceptions
     * ==========================
     */

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryNotFound(
        CategoryNotFoundException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryAlreadyExists(
        CategoryAlreadyExistsException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler({
        CategoryHierarchyException.class,
        CategoryStatusException.class,
        CategoryValidationException.class,
        CategoryHierarchyException.class,
        CategoryDeletionException.class,
        CategoryInUseException.class
    })
    public ResponseEntity<ApiErrorResponse> handleCategoryBusinessExceptions(
        RuntimeException ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            request
        );
    }

    /*
     * ==========================
     * Fallback
     * ==========================
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
        Exception ex,
        HttpServletRequest request) {

        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            request
        );
    }

    /*
     * ==========================
     * Helper Methods
     * ==========================
     */

    private ResponseEntity<ApiErrorResponse> buildResponse(
        HttpStatus status,
        String message,
        HttpServletRequest request) {

        return buildResponse(
            status,
            message,
            request,
            List.of()
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
        HttpStatus status,
        String message,
        HttpServletRequest request,
        List<String> validationErrors) {

        ApiErrorResponse response = ApiErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(message)
            .path(request.getRequestURI())
            .validationErrors(validationErrors)
            .build();

        return ResponseEntity
            .status(status)
            .body(response);
    }

}
