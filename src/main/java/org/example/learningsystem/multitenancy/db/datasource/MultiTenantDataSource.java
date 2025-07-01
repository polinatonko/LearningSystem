package org.example.learningsystem.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

/**
 * Implementation of {@link AbstractRoutingDataSource} that selects the appropriate target data source
 * based on the current tenant context.
 */
@Component
@Qualifier("multiTenantDataSource")
@RequiredArgsConstructor
@Slf4j
public class MultiTenantDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {

    private final DataSource defaultDataSource;
    private final TenantDataSourceManager tenantDataSourceManager;

    private Map<TenantInfo, DataSource> targetDataSources;

    @Override
    public void afterPropertiesSet() {
        Map<TenantInfo, DataSource> dataSources = tenantDataSourceManager.getAll();
        targetDataSources = new ConcurrentHashMap<>(dataSources);
        setDefaultTargetDataSource(defaultDataSource);
        updateTargetDataSources();
    }

    @Override
    public void destroy() throws Exception {
        targetDataSources.values()
                .forEach(this::tryToCloseDataSource);
        targetDataSources.clear();
        log.info("Tenant data sources were closed");
    }

    /**
     * Creates a new {@link DataSource} for the specified tenant.
     *
     * @param tenantInfo the tenant information
     * @return a new {@link DataSource} instance
     */
    public synchronized DataSource create(TenantInfo tenantInfo) {
        if (targetDataSources.containsKey(tenantInfo)) {
            return targetDataSources.get(tenantInfo);
        }

        DataSource dataSource = tenantDataSourceManager.create(tenantInfo);
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
        Optional.ofNullable(targetDataSources.get(tenantInfo))
                .ifPresent(this::tryToCloseDataSource);

        targetDataSources.remove(tenantInfo);
        updateTargetDataSources();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }

    private void updateTargetDataSources() {
        Map<Object, Object> dataSources = targetDataSources.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        setTargetDataSources(dataSources);
        initialize();
    }

    private void tryToCloseDataSource(DataSource dataSource) {
        try {
            dataSource.unwrap(AutoCloseable.class).close();
        } catch (Exception e) {
            log.error("Failed to close data source: {}", e.getMessage());
        }
    }
}
