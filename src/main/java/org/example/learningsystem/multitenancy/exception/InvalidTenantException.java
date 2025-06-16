package org.example.learningsystem.multitenancy.exception;

public class InvalidTenantException extends RuntimeException {

    public InvalidTenantException(String errMessage) {
        super(errMessage);
    }
}
