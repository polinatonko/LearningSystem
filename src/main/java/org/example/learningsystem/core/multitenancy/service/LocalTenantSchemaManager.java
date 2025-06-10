package org.example.learningsystem.core.multitenancy.service;

import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.util.DatabaseUtils;
import org.example.learningsystem.core.multitenancy.util.LiquibaseUtils;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.CREATE_SCHEMA_SQL;
import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.DROP_SCHEMA_SQL;
import static org.example.learningsystem.core.multitenancy.util.SchemaUtils.getSchemaName;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
@Slf4j
public class LocalTenantSchemaManager implements TenantSchemaManager {

    private final LiquibaseProperties liquibaseProperties;
    private final MultiTenantSpringLiquibase multiTenantSpringLiquibase;
    private final SpringLiquibase springLiquibase;

    @Override
    public void create(String tenantId) {
        var schema = getSchemaName(tenantId);
        log.info("Trying to create new schema {}", schema);

        var dataSource = springLiquibase.getDataSource();
        var sql = CREATE_SCHEMA_SQL.formatted(schema);
        DatabaseUtils.executeUpdate(dataSource, sql);
        log.info("Schema {} was created", schema);

        LiquibaseUtils.runForSchema(schema, multiTenantSpringLiquibase, liquibaseProperties);
    }

    @Override
    public void delete(String tenantId) {
        var schema = getSchemaName(tenantId);
        log.info("Trying to drop schema {}", schema);

        var dataSource = springLiquibase.getDataSource();
        var sql = DROP_SCHEMA_SQL.formatted(schema);
        DatabaseUtils.executeUpdate(dataSource, sql);
        log.info("Schema {} was dropped", schema);
    }
}
