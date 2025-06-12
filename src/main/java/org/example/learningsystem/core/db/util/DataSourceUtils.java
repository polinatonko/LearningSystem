package org.example.learningsystem.core.db.util;

import com.zaxxer.hikari.HikariDataSource;
import lombok.NoArgsConstructor;
import org.example.learningsystem.core.db.config.DataSourceProperties;

import javax.sql.DataSource;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DataSourceUtils {

    public static DataSource createDataSource(DataSourceProperties credentials) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(credentials.getUrl());
        dataSource.setUsername(credentials.getUsername());
        dataSource.setPassword(credentials.getPassword());
        return dataSource;
    }
}
