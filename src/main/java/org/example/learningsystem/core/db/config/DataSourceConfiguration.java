package org.example.learningsystem.core.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static org.example.learningsystem.core.db.util.DataSourceUtils.create;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceConfigurationProperties dataSourceConfigurationProperties) {
        return create(dataSourceConfigurationProperties);
    }
}
