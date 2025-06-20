package org.example.learningsystem.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@RequiredArgsConstructor
public class PostgreSQLConfiguration {

    private static final String POSTGRESQL_IMAGE = "postgres:17.4";

    private final DataSourceProperties dataSourceProperties;

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        var username = dataSourceProperties.getUsername();
        var password = dataSourceProperties.getPassword();
        return new PostgreSQLContainer<>(POSTGRESQL_IMAGE)
                .withUsername(username)
                .withPassword(password);
    }
}
