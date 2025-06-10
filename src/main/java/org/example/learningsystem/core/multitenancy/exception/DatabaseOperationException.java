package org.example.learningsystem.core.multitenancy.exception;

public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String errorMessage) {
        super(errorMessage);
    }
}
