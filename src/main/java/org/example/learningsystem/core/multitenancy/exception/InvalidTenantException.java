package org.example.learningsystem.core.multitenancy.exception;

public class InvalidTenantException extends RuntimeException {

    public InvalidTenantException(String tenant) {
        super("Invalid tenant provided [tenant = %s]".formatted(tenant));
    }
}
