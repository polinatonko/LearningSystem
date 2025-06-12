package org.example.learningsystem.core.multitenancy.db.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Profile("cloud")
@Slf4j
public class CloudMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final DataSource dataSource;

    public CloudMultiTenantConnectionProvider(@Qualifier("multiTenantDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.info("Getting connection for tenant: {}", tenantId);

        return dataSource.getConnection();
    }
}
