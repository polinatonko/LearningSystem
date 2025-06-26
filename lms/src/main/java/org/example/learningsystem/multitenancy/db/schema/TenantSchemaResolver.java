package org.example.learningsystem.multitenancy.db.schema;

/**
 * Interface for resolving between tenant identifiers and database schema names.
 */
public interface TenantSchemaResolver {

    /**
     * Resolves the database schema name for a given tenant id.
     *
     * @param tenantId the tenant identifier
     * @return the corresponding database schema name
     */
    String resolve(String tenantId);

    /**
     * Resolves the tenant id for a given database schema name.
     *
     * @param schema the database schema name
     * @return the corresponding tenant identifier
     */
    String resolveTenantId(String schema);
}
