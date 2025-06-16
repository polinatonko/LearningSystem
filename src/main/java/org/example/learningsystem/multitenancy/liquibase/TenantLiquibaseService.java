package org.example.learningsystem.multitenancy.liquibase;

import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaResolver;
import org.example.learningsystem.multitenancy.db.datasource.TenantDataSourceProvider;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.example.learningsystem.multitenancy.liquibase.LiquibaseUtils.getLiquibase;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantLiquibaseService {

    private final LiquibaseProperties liquibaseProperties;
    private final TenantDataSourceProvider tenantDataSourceProvider;
    private final TenantSchemaResolver tenantSchemaResolver;

    @PostConstruct
    public void runOnTenants() {
        var dataSources = tenantDataSourceProvider.getAll();
        dataSources.forEach(this::runOnTenant);
    }

    public void runOnTenant(TenantInfo tenantInfo, DataSource dataSource) {
        var schema = tenantSchemaResolver.resolve(tenantInfo.tenantId());
        try {
            var liquibase = getLiquibase(liquibaseProperties);
            liquibase.setDataSource(dataSource);
            liquibase.setDefaultSchema(schema);
            log.info("Running liquibase for {} schema", schema);
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new LiquibaseMigrationException(tenantInfo.tenantId(), schema);
        }
    }
}
