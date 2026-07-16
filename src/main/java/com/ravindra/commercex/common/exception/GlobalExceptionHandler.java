package com.ravindra.commercex.common.exception;

import com.ravindra.commercex.auth.exception.EmailAlreadyExistsException;
import com.ravindra.commercex.auth.exception.RoleNotFoundException;
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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(
        EmailAlreadyExistsException ex,
        HttpServletRequest request){

        ApiErrorResponse response =
            ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .validationErrors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(response);

    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleRoleNotFound(
        RoleNotFoundException ex,
        HttpServletRequest request){

        ApiErrorResponse response =
            ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .validationErrors(List.of())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(response);

    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
        MethodArgumentNotValidException ex,
        HttpServletRequest request){

        List<String> errors =
            ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiErrorResponse response =
            ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Bad Request")
                .message("Validation Failed")
                .path(request.getRequestURI())
                .validationErrors(errors)
                .build();

        return ResponseEntity.badRequest()
            .body(response);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(

        BadCredentialsException ex,

        HttpServletRequest request){

        ApiErrorResponse response =

            ApiErrorResponse.builder()

                .timestamp(LocalDateTime.now())

                .status(HttpStatus.UNAUTHORIZED.value())

                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())

                .message("Invalid email or password")

                .path(request.getRequestURI())

                .validationErrors(List.of())

                .build();

        return ResponseEntity

            .status(HttpStatus.UNAUTHORIZED)

            .body(response);

    }
}
