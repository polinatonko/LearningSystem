package org.example.learningsystem.multitenancy.exception;

/**
 * Exception thrown when invalid tenant identifier is provided (e.g., tenant does not exist, or id doesn't match
 * the required pattern).
 */
public class InvalidTenantIdentifierException extends InvalidTenantException {

    /**
     * Constructor for the {@link InvalidTenantIdentifierException}.
     *
     * @param tenantId the tenant identifier
     */
    public InvalidTenantIdentifierException(String tenantId) {
        super("Invalid tenant provided [tenantId = %s]".formatted(tenantId));
    }
}
