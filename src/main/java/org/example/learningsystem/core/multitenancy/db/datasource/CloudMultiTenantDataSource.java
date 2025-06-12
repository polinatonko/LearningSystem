package org.example.learningsystem.core.multitenancy.db.datasource;

import org.example.learningsystem.core.multitenancy.context.TenantContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.example.learningsystem.core.multitenancy.db.util.ConverterUtils.toObjectsMap;

@Component
@Profile("cloud")
@Qualifier("multiTenantDataSource")
public class CloudMultiTenantDataSource extends AbstractRoutingDataSource {

    public CloudMultiTenantDataSource(DataSource defaultDataSource, TenantDataSourceProvider tenantDataSourceProvider) {
        var resolvedDataSources = tenantDataSourceProvider.getAll();
        var dataSources = toObjectsMap(resolvedDataSources);
        setDefaultTargetDataSource(defaultDataSource);
        setTargetDataSources(dataSources);
        initialize();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenantId();
    }
}
