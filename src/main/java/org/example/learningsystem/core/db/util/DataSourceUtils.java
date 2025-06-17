package org.example.learningsystem.core.db.util;

import com.zaxxer.hikari.HikariDataSource;
import lombok.NoArgsConstructor;
import org.example.learningsystem.core.db.config.CustomDataSourceProperties;

import javax.sql.DataSource;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DataSourceUtils {

    public static DataSource create(CustomDataSourceProperties properties) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setSchema(properties.getSchema());
        return dataSource;
    }
}
