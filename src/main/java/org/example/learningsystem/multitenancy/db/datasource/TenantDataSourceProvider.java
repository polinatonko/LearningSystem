package org.example.learningsystem.multitenancy.db.datasource;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Interface for providing tenant-specific data sources.
 */
public interface TenantDataSourceProvider {

    /**
     * Returns map with tenant data sources.
     *
     * @return a map of tenant information to their corresponding data sources
     */
    Map<TenantInfo, DataSource> getAll();
}
