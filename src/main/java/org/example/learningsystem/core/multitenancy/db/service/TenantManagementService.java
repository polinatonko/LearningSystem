package org.example.learningsystem.core.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.example.learningsystem.core.multitenancy.db.datasource.MultiTenantDataSource;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaService;
import org.example.learningsystem.core.multitenancy.liquibase.TenantLiquibaseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantManagementService {

    private final MultiTenantDataSource multiTenantDataSource;
    private final TenantLiquibaseService tenantLiquibaseService;
    private final TenantSchemaService tenantSchemaService;

    public void create(String tenantId, String subdomain) {
        log.info("Starting onboarding process for new tenant: {}", tenantId);

        var tenantInfo = new TenantInfo(tenantId, subdomain);
        tenantSchemaService.create(tenantInfo);

        var dataSource = multiTenantDataSource.createDataSource(tenantInfo);
        tenantLiquibaseService.runOnTenant(tenantInfo, dataSource);

        log.info("Successfully completed onboarding for tenant: {}", tenantId);
    }

    public void delete(String tenantId, String subdomain) {
        log.info("Starting offboarding for tenant: {}", tenantId);

        var tenantInfo = new TenantInfo(tenantId, subdomain);
        tenantSchemaService.delete(tenantId);
        multiTenantDataSource.removeDataSource(tenantInfo);

        log.info("Successfully completed offboarding for tenant: {}", tenantId);
    }
}
