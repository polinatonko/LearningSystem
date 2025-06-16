package org.example.learningsystem.multitenancy.db.exception;

import org.example.learningsystem.multitenancy.exception.InvalidTenantException;

public class InvalidTenantSchemaException extends InvalidTenantException {

    public InvalidTenantSchemaException(String schema) {
        super("Tenant does not exist [schema = %s]".formatted(schema));
    }
}
