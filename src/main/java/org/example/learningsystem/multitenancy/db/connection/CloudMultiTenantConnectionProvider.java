package org.example.learningsystem.multitenancy.db.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Profile("cloud")
public class CloudMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    public CloudMultiTenantConnectionProvider(@Qualifier("multiTenantDataSource") DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.info("Getting connection for tenant: {}", tenantId);
        return getAnyConnection();
    }

    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
    }
}
