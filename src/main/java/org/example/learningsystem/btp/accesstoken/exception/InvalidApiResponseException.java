package org.example.learningsystem.btp.accesstoken.exception;

public class InvalidApiResponseException extends RuntimeException {

    public InvalidApiResponseException(String errorMessage) {
        super(errorMessage);
    }
}
