package org.example.learningsystem.core.multitenancy.db.exception;

import org.example.learningsystem.core.multitenancy.exception.InvalidTenantException;

public class InvalidTenantSchemaException extends InvalidTenantException {

    public InvalidTenantSchemaException(String schema) {
        super("Tenant does not exist [schema = %s]".formatted(schema));
    }
}
