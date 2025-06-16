package org.example.learningsystem.multitenancy.db.datasource;

import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.core.db.util.DataSourceUtils;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.util.TenantBindingUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Cloud implementation of {@link TenantDataSourceProvider} that creates and retrieves data sources
 * using SAP BTP service bindings.
 */
@Service
@Profile("cloud")
public class CloudTenantDataSourceProvider implements TenantDataSourceProvider {

    private final Map<TenantInfo, DataSource> dataSources;
    private final ServiceBindingManager serviceBindingManager;

    public CloudTenantDataSourceProvider(ServiceBindingManager serviceBindingManager) {
        this.dataSources = serviceBindingManager.getAll()
                .stream()
                .collect(Collectors.toMap(
                        TenantBindingUtils::extractTenantInfo,
                        binding -> DataSourceUtils.create(binding.credentials())));
        this.serviceBindingManager = serviceBindingManager;
    }

    @Override
    public DataSource create(TenantInfo tenant) {
        var binding = serviceBindingManager.getByTenantId(tenant.tenantId());
        var credentials = binding.credentials();
        return DataSourceUtils.create(credentials);
    }

    @Override
    public Map<TenantInfo, DataSource> getAll() {
        return dataSources;
    }
}
