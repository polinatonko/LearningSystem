package org.example.learningsystem.core.multitenancy.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class LiquibaseUtils {

    public static SpringLiquibase getLiquibase(
            SpringLiquibase springLiquibase, LiquibaseProperties liquibaseProperties) {
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
