package org.example.learningsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@RequiredArgsConstructor
public class PostgreSQLConfiguration {

    private final DataSourceProperties dataSourceProperties;

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:17.4")
                .withDatabaseName("lms")
                .withUsername(dataSourceProperties.getUsername())
                .withPassword(dataSourceProperties.getPassword());
    }

}
