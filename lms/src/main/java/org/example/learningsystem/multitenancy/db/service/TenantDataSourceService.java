package org.example.learningsystem.multitenancy.db.service;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;

/**
 * Interface for managing tenant data sources.
 */
public interface TenantDataSourceService {

    /**
     * Provides a {@link DataSource} instance for the tenant.
     *
     * @param tenant the tenant information
     * @return the tenant's data source
     */
    DataSource create(TenantInfo tenant);

    /**
     * Deletes a {@link DataSource} for the specified tenant
     *
     * @param tenant the tenant information
     */
    void delete(TenantInfo tenant);
}
