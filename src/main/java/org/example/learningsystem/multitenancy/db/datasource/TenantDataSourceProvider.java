package org.example.learningsystem.multitenancy.db.datasource;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Interface for providing tenant-specific data sources.
 */
public interface TenantDataSourceProvider {

    /**
     * Creates or retrieves a {@link DataSource} for specified tenant.
     *
     * @param tenant the tenant information
     * @return a configured {@link DataSource} for tenant
     */
    DataSource create(TenantInfo tenant);

    /**
     * Returns map with tenant data sources.
     *
     * @return a map of tenant information to their corresponding data sources
     */
    Map<TenantInfo, DataSource> getAll();
}
