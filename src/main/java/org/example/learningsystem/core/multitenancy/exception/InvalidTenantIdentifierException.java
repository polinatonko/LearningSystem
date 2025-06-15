package org.example.learningsystem.core.multitenancy.exception;

public class InvalidTenantIdentifierException extends InvalidTenantException {

    public InvalidTenantIdentifierException(String tenantId) {
        super("Invalid tenant provided [tenantId = %s]".formatted(tenantId));
    }
}
