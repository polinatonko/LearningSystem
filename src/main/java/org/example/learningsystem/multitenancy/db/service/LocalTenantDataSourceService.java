package org.example.learningsystem.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

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
