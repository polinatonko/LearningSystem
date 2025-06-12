package org.example.learningsystem.core.multitenancy.db.datasource;

import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

import static org.example.learningsystem.core.db.util.DataSourceUtils.createDataSource;
import static org.example.learningsystem.core.multitenancy.db.util.ConverterUtils.toObjectsMap;

@Service
@Profile("cloud")
public class CloudTenantDataSourceService implements TenantDataSourceService {

    private final CloudMultiTenantDataSource cloudMultiTenantDataSource;
    private final Map<String, DataSource> dataSources;
    private final ServiceBindingManager serviceBindingManager;

    public CloudTenantDataSourceService(
            CloudMultiTenantDataSource cloudMultiTenantDataSource,
            ServiceBindingManager serviceBindingManager,
            TenantDataSourceProvider tenantDataSourceProvider) {
        this.cloudMultiTenantDataSource = cloudMultiTenantDataSource;
        this.dataSources = tenantDataSourceProvider.getAll();
        this.serviceBindingManager = serviceBindingManager;
    }

    @Override
    public DataSource create(String tenantId) {
        var binding = serviceBindingManager.getByTenantId(tenantId);

        var credentials = binding.credentials();
        var dataSource = createDataSource(credentials);
        dataSources.put(tenantId, dataSource);
        updateDataSources(dataSources);

        return dataSource;
    }

    @Override
    public void delete(String tenantId) {
        dataSources.remove(tenantId);
        updateDataSources(dataSources);
    }

    private void updateDataSources(Map<String, DataSource> dataSources) {
        var mappedDataSources = toObjectsMap(dataSources);
        cloudMultiTenantDataSource.setTargetDataSources(mappedDataSources);
        cloudMultiTenantDataSource.initialize();
    }
}
