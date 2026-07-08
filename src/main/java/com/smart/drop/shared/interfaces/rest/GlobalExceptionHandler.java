package com.smart.drop.shared.interfaces.rest;

import com.smart.drop.iam.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MethodArgumentNotValidException ex) {
        String message = ex.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage() == null ? "" : fieldError.getDefaultMessage()).reduce("", String::concat);
        return ErrorResponse.create(
                ex,
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
                message
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(IllegalArgumentException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
    }

    // Excepciones del módulo Identity

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleException(UserNotFoundException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleException(RoleNotFoundException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleException(UserAlreadyExistsException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), ex.getMessage());
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleException(RoleAlreadyExistsException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyHasRoleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleException(UserAlreadyHasRoleException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleException(Exception ex) {
        ex.printStackTrace();
        return ErrorResponse.create(
                ex,
                HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Generic server exception: " + (ex.getMessage() != null ? ex.getMessage() : ex.toString())
        );
    }

}
