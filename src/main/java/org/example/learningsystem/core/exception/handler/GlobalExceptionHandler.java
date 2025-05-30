package org.example.learningsystem.core.exception.handler;

import org.example.learningsystem.btp.accesstoken.exception.InvalidApiResponseException;
import org.example.learningsystem.core.exception.response.ErrorResponse;
import org.example.learningsystem.core.exception.logic.EntityNotFoundException;
import org.example.learningsystem.core.exception.validation.IllegalNullValueException;
import org.example.learningsystem.student.exception.InsufficientBirthDateException;
import org.example.learningsystem.course.exception.InvalidCourseDurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        var errorMessage = e.getMessage();
        return ErrorResponse.of(errorMessage, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        var errorMessage = e.getReason();
        var responseStatus = (HttpStatus) e.getStatusCode();
        var errorResponse = ErrorResponse.of(errorMessage, responseStatus);
        return ResponseEntity.status(responseStatus).body(errorResponse);
    }

    @ExceptionHandler({
            InsufficientBirthDateException.class,
            InvalidCourseDurationException.class,
            IllegalNullValueException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(RuntimeException e) {
        var errorMessage = e.getMessage();
        return ErrorResponse.of(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleHttpMessageConversionException(HttpMessageConversionException e) {
        var cause = e.getMostSpecificCause();
        var errorMessage = cause.getMessage();
        return ErrorResponse.of(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldsValidationError = buildFieldsValidationErrorMessage(e);
        var errorMessage = "Validation failed: %s".formatted(fieldsValidationError);
        return ErrorResponse.of(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        var cause = e.getMostSpecificCause();
        var causeMessage = cause.getMessage();
        var errorMessage = "Argument type mismatch: %s: %s".formatted(
                e.getName(),
                causeMessage);
        return ErrorResponse.of(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        var errorMessage = e.getMessage();
        return ErrorResponse.of(errorMessage, NOT_FOUND);
    }

    @ExceptionHandler(InvalidApiResponseException.class)
    @ResponseStatus(BAD_GATEWAY)
    public ErrorResponse handleInvalidApiResponseException(InvalidApiResponseException e) {
        var errorMessage = e.getMessage();
        return ErrorResponse.of(errorMessage, BAD_GATEWAY);
    }

    private String buildFieldsValidationErrorMessage(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        return bindingResult.getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }

}