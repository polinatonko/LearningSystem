package org.example.learningsystem.multitenancy.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class LiquibaseUtils {

    public static SpringLiquibase getLiquibase(LiquibaseProperties liquibaseProperties) {
        var liquibase = new SpringLiquibase();
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setClearCheckSums(liquibaseProperties.isClearChecksums());
        var contexts = collectionToCommaDelimitedString(liquibaseProperties.getContexts());
        liquibase.setContexts(contexts);
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        return liquibase;
    }
}
