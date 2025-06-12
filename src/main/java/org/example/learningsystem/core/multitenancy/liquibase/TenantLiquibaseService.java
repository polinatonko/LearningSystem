package org.example.learningsystem.core.multitenancy.liquibase;

import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaResolver;
import org.example.learningsystem.core.multitenancy.db.datasource.TenantDataSourceProvider;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.example.learningsystem.core.multitenancy.liquibase.LiquibaseUtils.getLiquibase;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantLiquibaseService {

    private final LiquibaseProperties liquibaseProperties;
    private final SpringLiquibase springLiquibase;
    private final TenantDataSourceProvider tenantDataSourceProvider;
    private final TenantSchemaResolver tenantSchemaResolver;

    @PostConstruct
    public void runOnTenants() {
        var dataSources = tenantDataSourceProvider.getAll();
        dataSources.forEach(this::runOnTenant);
    }

    public void runOnTenant(String tenantId, DataSource dataSource) {
        var schema = tenantSchemaResolver.resolveSchema(tenantId);
        try {
            var liquibase = createLiquibase(dataSource, schema);
            log.info("Running liquibase for {} schema", schema);
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new LiquibaseMigrationException(tenantId, schema);
        }
    }

    private SpringLiquibase createLiquibase(DataSource dataSource, String schema) {
        var liquibase = getLiquibase(springLiquibase, liquibaseProperties);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        return liquibase;
    }
}
