package org.example.learningsystem.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Local implementation of {@link TenantDataSourceService} that uses a single shared data source for all tenants.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceService implements TenantDataSourceService {

    private final DataSource dataSource;

    @Override
    public DataSource create(TenantInfo tenant) {
        return dataSource;
    }

    @Override
    public void delete(TenantInfo tenant) {
    }
}
