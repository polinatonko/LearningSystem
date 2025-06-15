package org.example.learningsystem.core.multitenancy.exception;

public class InvalidTenantException extends RuntimeException {

    public InvalidTenantException(String errMessage) {
        super(errMessage);
    }
}
