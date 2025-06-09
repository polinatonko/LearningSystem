package org.example.learningsystem.core.multitenancy.service;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.util.LiquibaseUtils;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
@Slf4j
public class LocalSchemaManager implements SchemaManager {

    private static final String CREATE_SCHEMA_SQL = "CREATE SCHEMA %s";
    private static final String DROP_SCHEMA_SQL = "DROP SCHEMA %s CASCADE";

    private final LiquibaseProperties liquibaseProperties;
    private final SpringLiquibase springLiquibase;

    @Override
    public void create(String subdomain) {
        var schema = getSchemaName(subdomain);
        log.info("Trying to create new schema {}", schema);

        var dataSource = springLiquibase.getDataSource();
        try (var connection = dataSource.getConnection()) {
            var sql = CREATE_SCHEMA_SQL.formatted(schema);
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create schema %s: %s".formatted(schema, e.getMessage()));
        }

        runLiquibase(schema);
    }

    @Override
    public void delete(String subdomain) {
        var schema = getSchemaName(subdomain);
        log.info("Trying to drop schema {}", schema);

        var dataSource = springLiquibase.getDataSource();
        try (var connection = dataSource.getConnection()) {
            var sql = DROP_SCHEMA_SQL.formatted(schema);
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop schema %s: %s".formatted(schema, e.getMessage()));
        }
    }

    private String getSchemaName(String subdomain) {
        return subdomain.replace("-", "_");
    }

    private void runLiquibase(String schema) {
        try {
            var liquibase = LiquibaseUtils.getLiquibase(springLiquibase, liquibaseProperties);
            liquibase.setDefaultSchema(schema);
            log.info("Running liquibase for {} schema", schema);
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new RuntimeException("Failed to run liquibase for schema %s".formatted(schema));
        }
    }
}
