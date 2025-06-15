package org.example.learningsystem.core.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.core.db.util.DataSourceHelper;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.example.learningsystem.core.multitenancy.db.util.TenantUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantDataSourceProvider implements TenantDataSourceProvider {

    private final ServiceBindingManager serviceBindingManager;

    @Override
    public DataSource create(TenantInfo tenant) {
        var binding = serviceBindingManager.getByTenantId(tenant.tenantId());
        var credentials = binding.credentials();
        return DataSourceHelper.create(credentials);
    }

    @Override
    public Map<TenantInfo, DataSource> getAll() {
        return serviceBindingManager.getAll()
                .stream()
                .collect(Collectors.toMap(
                        TenantUtils::extractTenantInfo,
                        binding -> DataSourceHelper.create(binding.credentials())));
    }
}
