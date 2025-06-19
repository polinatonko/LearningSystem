package org.example.learningsystem.multitenancy.db.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaService;
import org.example.learningsystem.multitenancy.liquibase.TenantLiquibaseService;
import org.springframework.stereotype.Service;

/**
 * Service for coordinating the onboarding and offboarding database-related processes for tenants.
 * <p>
 * Manages schema creation and deletion, data source configuration, and executing database migrations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantDatabaseManagementService {

    private final TenantDataSourceService tenantDataSourceService;
    private final TenantLiquibaseService tenantLiquibaseService;
    private final TenantSchemaService tenantSchemaService;

    /**
     * Creates database schema for the specified tenant.
     *
     * @param tenantId  the tenant identifier
     * @param subdomain the tenant's subdomain
     */
    public void createSchema(String tenantId, String subdomain) {
        log.info("Starting onboarding process for new tenant: {}", tenantId);

        var tenantInfo = new TenantInfo(tenantId, subdomain);
        tenantSchemaService.create(tenantInfo);

        var dataSource = tenantDataSourceService.create(tenantInfo);
        tenantLiquibaseService.runOnTenant(tenantInfo, dataSource);

        log.info("Successfully completed onboarding for tenant: {}", tenantId);
    }

    /**
     * Deletes database schema for the specified tenant.
     *
     * @param tenantId  the tenant identifier
     * @param subdomain the tenant's subdomain
     */
    public void deleteSchema(String tenantId, String subdomain) {
        log.info("Starting offboarding for tenant: {}", tenantId);

        var tenantInfo = new TenantInfo(tenantId, subdomain);
        tenantSchemaService.delete(tenantId);
        tenantDataSourceService.delete(tenantInfo);

        log.info("Successfully completed offboarding for tenant: {}", tenantId);
    }
}
