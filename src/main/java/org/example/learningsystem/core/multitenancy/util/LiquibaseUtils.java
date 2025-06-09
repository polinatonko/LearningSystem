package org.example.learningsystem.core.multitenancy.util;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

public class LiquibaseUtils {

    public static SpringLiquibase getLiquibase(SpringLiquibase springLiquibase, LiquibaseProperties liquibaseProperties) {
        var liquibase = new SpringLiquibase();
        liquibase.setChangeLog(springLiquibase.getChangeLog());
        liquibase.setContexts(springLiquibase.getContexts());
        liquibase.setDataSource(springLiquibase.getDataSource());
        liquibase.setResourceLoader(springLiquibase.getResourceLoader());
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
