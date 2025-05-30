package org.example.learningsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@RequiredArgsConstructor
public class PostgreSQLConfiguration {

    private static final String POSTGRESQL_IMAGE = "postgres:17.4";
    private static final String DATABASE_NAME = "lms";

    private final DataSourceProperties dataSourceProperties;

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>(POSTGRESQL_IMAGE)
                .withDatabaseName(DATABASE_NAME)
                .withUsername(dataSourceProperties.getUsername())
                .withPassword(dataSourceProperties.getPassword());
    }

}
