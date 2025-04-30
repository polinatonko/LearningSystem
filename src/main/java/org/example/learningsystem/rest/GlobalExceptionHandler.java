package org.example.learningsystem.rest;

import org.example.learningsystem.dto.ErrorResponseDto;
import org.example.learningsystem.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(Exception e) {
        return new ErrorResponseDto(e.getMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleHttpMessageConversionException(HttpMessageConversionException e) {
        return new ErrorResponseDto(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        var errMessage = String.format("Validation failed: %s", String.join("; ", errors));
        return new ErrorResponseDto(errMessage, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        var errMessage = String.format("Argument type mismatch: %s: %s", e.getName(), e.getMostSpecificCause().getMessage());
        return new ErrorResponseDto(errMessage, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}