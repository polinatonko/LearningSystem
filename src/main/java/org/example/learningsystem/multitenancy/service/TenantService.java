package org.example.learningsystem.multitenancy.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.datasource.MultiTenantDataSource;
import org.springframework.stereotype.Service;

/**
 * Service that executes operations across all tenants.
 * <p>
 * Ensures that the operation runs once for every tenant and that {@link TenantContext}
 * is properly cleared after execution.
 */
@Service
@RequiredArgsConstructor
public class TenantService {

    private final MultiTenantDataSource multiTenantDataSource;

    /**
     * Executes an operation for all existing tenants.
     *
     * @param runnable the operation to execute
     */
    public void executeForAll(Runnable runnable) {
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
