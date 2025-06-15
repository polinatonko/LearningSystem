package org.example.learningsystem.core.db.util;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.db.config.DataSourceConfigurationProperties;
import org.example.learningsystem.core.db.config.DataSourceProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class DataSourceHelper {

    private final DataSourceConfigurationProperties dataSourceConfigurationProperties;

    public DataSource createForSchema(String schema) {
        var dataSourceCredentials = new DataSourceConfigurationProperties();
        dataSourceCredentials.setUrl(dataSourceConfigurationProperties.getUrl());
        dataSourceCredentials.setUsername(dataSourceConfigurationProperties.getUsername());
        dataSourceCredentials.setPassword(dataSourceConfigurationProperties.getPassword());
        dataSourceCredentials.setSchema(schema);
        return create(dataSourceCredentials);
    }

    public static DataSource create(DataSourceProperties credentials) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(credentials.getUrl());
        dataSource.setUsername(credentials.getUsername());
        dataSource.setPassword(credentials.getPassword());
        if (nonNull(credentials.getSchema())) {
            dataSource.setSchema(credentials.getSchema());
        }
        return dataSource;
    }
}
