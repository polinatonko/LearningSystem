package org.example.learningsystem.core.multitenancy.util;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.exception.DatabaseOperationException;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class LiquibaseUtils {

    public static void runForSchema(String schema, MultiTenantSpringLiquibase multiTenantSpringLiquibase,
                                    LiquibaseProperties liquibaseProperties) {
        try {
            var changeLog = multiTenantSpringLiquibase.getChangeLog();
            var liquibase = getLiquibase(multiTenantSpringLiquibase, liquibaseProperties);
            liquibase.setDefaultSchema(schema);
            liquibase.setChangeLog(changeLog);
            log.info("Running liquibase for {} schema", schema);
            liquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            throw new DatabaseOperationException("Failed to run liquibase for schema %s".formatted(schema));
        }
    }

    private static SpringLiquibase getLiquibase(
            MultiTenantSpringLiquibase springLiquibase, LiquibaseProperties liquibaseProperties) {
        var liquibase = new SpringLiquibase();
        liquibase.setChangeLog(springLiquibase.getChangeLog());
        liquibase.setContexts(springLiquibase.getContexts());
        liquibase.setDataSource(springLiquibase.getDataSource());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setClearCheckSums(liquibaseProperties.isClearChecksums());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        return liquibase;
    }
}
