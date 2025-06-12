package org.example.learningsystem.core.multitenancy.db.datasource;

import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.learningsystem.btp.servicemanager.common.constant.ServiceManagerResourceConstants.TENANT_ID;
import static org.example.learningsystem.core.db.util.DataSourceUtils.createDataSource;

@Service
@Profile("cloud")
@DependsOn("serviceBindingManager")
public class CloudTenantDataSourceProvider implements TenantDataSourceProvider {

    private final Map<String, DataSource> dataSources;
    private final ServiceBindingManager serviceBindingManager;

    public CloudTenantDataSourceProvider(ServiceBindingManager serviceBindingManager) {
        this.serviceBindingManager = serviceBindingManager;
        this.dataSources = initializeDataSources();
    }

    @Override
    public Map<String, DataSource> getAll() {
        return dataSources;
    }

    private Map<String, DataSource> initializeDataSources() {
        return serviceBindingManager.getAll()
                .stream()
                .collect(Collectors.toMap(this::extractTenantId, this::createTenantDataSource));
    }

    private String extractTenantId(ServiceBindingResponseDto responseDto) {
        return responseDto.labels().get(TENANT_ID).getFirst();
    }

    private DataSource createTenantDataSource(ServiceBindingResponseDto binding) {
        var credentials = binding.credentials();
        return createDataSource(credentials);
    }
}
