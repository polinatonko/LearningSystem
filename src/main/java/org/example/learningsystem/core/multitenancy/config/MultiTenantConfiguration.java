package org.example.learningsystem.core.multitenancy.config;

import liquibase.integration.spring.MultiTenantSpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.service.TenantSchemaProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

import java.util.ArrayList;

@Slf4j
@Configuration
public class MultiTenantConfiguration {

    private final String liquibaseChangelog;
    private final TenantSchemaProvider tenantSchemaProvider;

    public MultiTenantConfiguration(
            @Value("${spring.liquibase.change-log}") String liquibaseChangelog, TenantSchemaProvider tenantSchemaProvider) {
        this.liquibaseChangelog = liquibaseChangelog;
        this.tenantSchemaProvider = tenantSchemaProvider;
    }

    @Bean
    @DependsOn("liquibase")
    public MultiTenantSpringLiquibase multiTenantSpringLiquibase(DataSource dataSource) {
        var schemas = new ArrayList<String>(); // tenantSchemaProvider.getSchemaNames();
        log.info("Schemas: {}", schemas);
        var liquibase = new MultiTenantSpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setSchemas(schemas);
        liquibase.setChangeLog(liquibaseChangelog);
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
