package org.example.learningsystem.multitenancy.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.datasource.MultiTenantDataSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final MultiTenantDataSource multiTenantDataSource;

    public void executeForAllTenants(Runnable runnable) {
        try {
            runnable.run();

            var dataSources = multiTenantDataSource.getResolvedDataSources();
            for (var tenant : dataSources.keySet()) {
                TenantContext.setTenant((TenantInfo) tenant);
                runnable.run();
            }
        } finally {
            TenantContext.clear();
        }
    }
}
