package org.example.learningsystem.exception.handler;

import org.example.learningsystem.exception.response.ErrorResponse;
import org.example.learningsystem.exception.logic.EntityNotFoundException;
import org.example.learningsystem.exception.validation.InsufficientBirthDateException;
import org.example.learningsystem.exception.validation.InvalidCourseDurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        var message = e.getMessage();
        return toDto(message, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InsufficientBirthDateException.class, InvalidCourseDurationException.class})
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(RuntimeException e) {
        var errorMessage = e.getMessage();
        return toDto(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleHttpMessageConversionException(HttpMessageConversionException e) {
        var cause = e.getMostSpecificCause();
        var errorMessage = cause.getMessage();
        return toDto(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldsValidationError = buildFieldsValidationErrorMessage(e);
        var errorMessage = "Validation failed: %s".formatted(fieldsValidationError);
        return toDto(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        var cause = e.getMostSpecificCause();
        var causeMessage = cause.getMessage();
        var errorMessage = "Argument type mismatch: %s: %s".formatted(
                e.getName(),
                causeMessage);
        return toDto(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        var errorMessage = e.getMessage();
        return toDto(errorMessage, NOT_FOUND);
    }

    private String buildFieldsValidationErrorMessage(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        return bindingResult.getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }

    private static ErrorResponse toDto(String errorMessage, HttpStatus status) {
        return new ErrorResponse(errorMessage, status.value());
    }
}