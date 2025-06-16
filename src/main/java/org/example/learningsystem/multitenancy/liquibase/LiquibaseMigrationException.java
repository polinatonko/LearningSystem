package org.example.learningsystem.multitenancy.liquibase;

public class LiquibaseMigrationException extends RuntimeException {

    public LiquibaseMigrationException(String tenantId, String schema) {
        super("Failed to run liquibase for tenant [tenantId = %s, schema = %s]".formatted(tenantId, schema));
    }
}
