package org.example.learningsystem.multitenancy.db.migration;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;

/**
 * Interface for managing database migrations for tenant schemas.
 */
public interface DatabaseMigrationRunner {

    /**
     * Runs database migrations for all existing tenants.
     */
    void runOnTenants();

    /**
     * Runs database migrations for the specified tenant.
     *
     * @param tenantInfo the tenant information
     * @param dataSource the tenant datasource to be used
     */
    void runOnTenant(TenantInfo tenantInfo, DataSource dataSource);
}
