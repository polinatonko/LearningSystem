package org.example.learningsystem.multitenancy.db.liquibase;

import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.migration.DatabaseMigrationRunner;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaResolver;
import org.example.learningsystem.multitenancy.db.datasource.TenantDataSourceManager;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.example.learningsystem.multitenancy.db.liquibase.LiquibaseUtils.getLiquibase;

/**
 * Implementation of {@link DatabaseMigrationRunner} for Liquibase.
 * <p>
 * Handles running migrations for both new tenants in runtime and existing tenants during application startup.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiquibaseMigrationRunner implements DatabaseMigrationRunner {

    private final LiquibaseProperties liquibaseProperties;
    private final TenantDataSourceManager tenantDataSourceManager;
    private final TenantSchemaResolver tenantSchemaResolver;

    @PostConstruct
    @Override
    public void runOnTenants() {
        var dataSources = tenantDataSourceManager.getAll();
        dataSources.forEach(this::runOnTenant);
    }

    @Override
    public void runOnTenant(TenantInfo tenantInfo, DataSource dataSource) {
        var schema = tenantSchemaResolver.resolve(tenantInfo.tenantId());
        try {
            var liquibase = getLiquibase(liquibaseProperties);
            liquibase.setDataSource(dataSource);
            liquibase.setDefaultSchema(schema);
            log.info("Running Liquibase for tenant schema [name = {}]", schema);
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new LiquibaseMigrationException(tenantInfo.tenantId(), schema);
        }
    }
}
