package org.example.learningsystem.multitenancy.db.datasource;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.core.db.util.DataSourceUtils;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.beans.factory.DisposableBean;
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
@Slf4j
public class MultiTenantDataSource extends AbstractRoutingDataSource implements DisposableBean {

    private final ServiceBindingManager serviceBindingManager;
    private final Map<Object, Object> targetDataSources;

    public MultiTenantDataSource(DataSource defaultDataSource,
                                 ServiceBindingManager serviceBindingManager,
                                 TenantDataSourceProvider tenantDataSourceProvider) {
        this.serviceBindingManager = serviceBindingManager;
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
    public synchronized DataSource create(TenantInfo tenantInfo) {
        if (targetDataSources.containsKey(tenantInfo)) {
            return (DataSource) targetDataSources.get(tenantInfo);
        }

        var serviceBinding = serviceBindingManager.getByTenantId(tenantInfo.tenantId());
        var credentials = serviceBinding.credentials();
        var dataSource = DataSourceUtils.create(credentials);

        targetDataSources.put(tenantInfo, dataSource);
        updateTargetDataSources();
        return dataSource;
    }

    /**
     * Deletes {@link DataSource} associated with the specified tenant.
     *
     * @param tenantInfo the tenant information
     */
    public synchronized void delete(TenantInfo tenantInfo) {
        targetDataSources.remove(tenantInfo);
        updateTargetDataSources();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }

    private void updateTargetDataSources() {
        setTargetDataSources(targetDataSources);
        initialize();
    }

    @Override
    public void destroy() throws Exception {
        targetDataSources.values()
                .forEach(this::tryToCloseDataSource);
        targetDataSources.clear();
        log.info("Tenant data sources were closed");
    }

    private void tryToCloseDataSource(Object dataSource) {
        try {
            ((DataSource) dataSource).unwrap(AutoCloseable.class).close();
        } catch (Exception e) {
            log.error("Failed to close data source: {}", e.getMessage());
        }
    }
}
