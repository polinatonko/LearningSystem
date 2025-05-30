package org.example.learningsystem.core.web.exception;

public class InvalidApiResponseException extends RuntimeException {

    public InvalidApiResponseException(String errorMessage) {
        super(errorMessage);
    }

}
