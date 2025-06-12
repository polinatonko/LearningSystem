package org.example.learningsystem.core.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.db.datasource.TenantDataSourceService;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaService;
import org.example.learningsystem.core.multitenancy.liquibase.TenantLiquibaseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantManagementService {

    private final TenantDataSourceService tenantDataSourceService;
    private final TenantSchemaService tenantSchemaService;
    private final TenantLiquibaseService tenantLiquibaseService;

    public void create(String tenantId) {
        log.info("Starting onboarding process for new tenant: {}", tenantId);

        tenantSchemaService.create(tenantId);

        var dataSource = tenantDataSourceService.create(tenantId);
        tenantLiquibaseService.runOnTenant(tenantId, dataSource);

        log.info("Successfully completed onboarding for tenant: {}", tenantId);
    }

    public void delete(String tenantId) {
        log.info("Starting offboarding for tenant: {}", tenantId);

        tenantSchemaService.delete(tenantId);
        tenantDataSourceService.delete(tenantId);

        log.info("Successfully completed offboarding for tenant: {}", tenantId);
    }
}
