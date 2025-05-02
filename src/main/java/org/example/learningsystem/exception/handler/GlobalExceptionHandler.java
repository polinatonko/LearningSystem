package org.example.learningsystem.exception.handler;

import org.example.learningsystem.exception.response.ErrorResponse;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return toDto(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleHttpMessageConversionException(HttpMessageConversionException e) {
        return toDto(e.getMostSpecificCause().getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        var errMessage = String.format("Validation failed: %s", String.join("; ", errors));
        return toDto(errMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        var errMessage = String.format("Argument type mismatch: %s: %s",
                e.getName(), e.getMostSpecificCause().getMessage());
        return toDto(errMessage, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return toDto(e.getMessage(), NOT_FOUND);
    }

    private static ErrorResponse toDto(String message, HttpStatus status) {
        return new ErrorResponse(message, status.value());
    }
}