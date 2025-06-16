package org.example.learningsystem.multitenancy.db.datasource;

import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link AbstractRoutingDataSource} that selects the appropriate target data source
 * based on the current tenant context.
 */
@Component
@Qualifier("multiTenantDataSource")
public class MultiTenantDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> targetDataSources;
    private final TenantDataSourceProvider tenantDataSourceProvider;

    public MultiTenantDataSource(DataSource defaultDataSource, TenantDataSourceProvider tenantDataSourceProvider) {
        this.tenantDataSourceProvider = tenantDataSourceProvider;
        var dataSources = tenantDataSourceProvider.getAll();
        targetDataSources = new ConcurrentHashMap<>(dataSources);
        setDefaultTargetDataSource(defaultDataSource);
        setTargetDataSources(targetDataSources);
        initialize();
    }

    /**
     * Creates a new {@link DataSource} for the specified tenant.
     *
     * @param tenantInfo the tenant information
     * @return a new {@link DataSource} instance
     */
    public DataSource createDataSource(TenantInfo tenantInfo) {
        var dataSource = tenantDataSourceProvider.create(tenantInfo);
        targetDataSources.put(tenantInfo, dataSource);
        updateTargetDataSources();
        return dataSource;
    }

    /**
     * Deletes {@link DataSource} associated with the specified tenant.
     *
     * @param tenantInfo the tenant information
     */
    public void removeDataSource(TenantInfo tenantInfo) {
        targetDataSources.remove(tenantInfo);
        updateTargetDataSources();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }

    private synchronized void updateTargetDataSources() {
        setTargetDataSources(targetDataSources);
        initialize();
    }
}
