package org.example.learningsystem.core.multitenancy.exception;

public class InvalidTenantException extends RuntimeException {

    public InvalidTenantException(String tenantId) {
        super("Invalid tenant provided [tenantId = %s]".formatted(tenantId));
    }
}
