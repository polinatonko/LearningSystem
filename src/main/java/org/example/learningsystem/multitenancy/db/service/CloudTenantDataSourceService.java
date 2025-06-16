package org.example.learningsystem.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.datasource.MultiTenantDataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantDataSourceService implements TenantDataSourceService {

    private final MultiTenantDataSource multiTenantDataSource;

    @Override
    public DataSource create(TenantInfo tenant) {
        return multiTenantDataSource.createDataSource(tenant);
    }

    @Override
    public void delete(TenantInfo tenant) {
        multiTenantDataSource.removeDataSource(tenant);
    }
}
