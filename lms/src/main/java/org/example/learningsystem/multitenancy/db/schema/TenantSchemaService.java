package org.example.learningsystem.multitenancy.db.schema;

import org.example.learningsystem.multitenancy.context.TenantInfo;

/**
 * Interface for managing tenant database schemas.
 */
public interface TenantSchemaService {

    /**
     * Creates a database schema for the specified tenant.
     *
     * @param tenantInfo the tenant information
     */
    void create(TenantInfo tenantInfo);

    /**
     * Deletes the database schema for the specified tenant.
     *
     * @param tenantId the tenant identifier
     */
    void delete(String tenantId);
}
