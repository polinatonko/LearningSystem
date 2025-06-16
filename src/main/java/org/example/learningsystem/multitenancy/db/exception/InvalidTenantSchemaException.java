package org.example.learningsystem.multitenancy.db.exception;

import org.example.learningsystem.multitenancy.exception.InvalidTenantException;

/**
 * Exception thrown when an invalid tenant schema is provided (tenant does not exist).
 */
public class InvalidTenantSchemaException extends InvalidTenantException {

    public InvalidTenantSchemaException(String schema) {
        super("Tenant does not exist [schema = %s]".formatted(schema));
    }
}
