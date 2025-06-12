package org.example.learningsystem.core.multitenancy.db.connection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Profile("!cloud")
@Slf4j
@RequiredArgsConstructor
public class LocalMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final DataSource dataSource;
    private final MultitenancyProperties multitenancyProperties;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection(multitenancyProperties.getDefaultTenant());
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.info("Getting connection for tenant: {}", tenantId);

        var tenantSchema = tenantSchemaResolver.resolveSchema(tenantId);
        var connection = dataSource.getConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        var defaultTenant = multitenancyProperties.getDefaultTenant();
        var tenantSchema = tenantSchemaResolver.resolveSchema(defaultTenant);
        connection.setSchema(tenantSchema);
        super.releaseConnection(tenantId, connection);
    }
}
