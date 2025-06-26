package org.example.learningsystem.multitenancy.exception;

/**
 * Exception throws when invalid information about tenant is provided.
 */
public class InvalidTenantException extends RuntimeException {

    public InvalidTenantException(String errMessage) {
        super(errMessage);
    }
}
