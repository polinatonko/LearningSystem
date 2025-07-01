package org.example.learningsystem.multitenancy.db.liquibase;

import org.example.learningsystem.core.exception.model.LearningManagementSystemException;

/**
 * Exception thrown when an error occurs during running Liquibase database migration.
 */
public class LiquibaseMigrationException extends LearningManagementSystemException {

    /**
     * Constructor for the {@link LiquibaseMigrationException}.
     *
     * @param tenantId the tenant identifier for which migration failed
     * @param schema   the database schema where migration failed
     */
    public LiquibaseMigrationException(String tenantId, String schema) {
        super("Failed to run Liquibase for tenant [tenantId = %s, schema = %s]".formatted(tenantId, schema));
    }
}
