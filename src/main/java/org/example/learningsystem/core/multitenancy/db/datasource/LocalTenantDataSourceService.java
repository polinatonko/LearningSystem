package org.example.learningsystem.core.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceService implements TenantDataSourceService {

    private final DataSource dataSource;

    @Override
    public DataSource create(String tenantId) {
        return dataSource;
    }

    @Override
    public void delete(String tenantId) {

    }
}
