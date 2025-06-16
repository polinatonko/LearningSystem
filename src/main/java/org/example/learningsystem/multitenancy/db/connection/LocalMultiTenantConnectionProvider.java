package org.example.learningsystem.multitenancy.db.connection;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Local implementation of {@link AbstractMultiTenantConnectionProvider} that configures
 * connections to use tenant-specific schemas on a shared database instance.
 */
@Component
@Slf4j
@Profile("!cloud")
public class LocalMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final MultitenancyProperties multitenancyProperties;
    private final TenantSchemaResolver tenantSchemaResolver;

    public LocalMultiTenantConnectionProvider(DataSource dataSource,
                                              MultitenancyProperties multitenancyProperties,
                                              TenantSchemaResolver tenantSchemaResolver) {
        super(dataSource);
        this.multitenancyProperties = multitenancyProperties;
        this.tenantSchemaResolver = tenantSchemaResolver;
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.info("Getting connection for tenant: {}", tenantId);
        var connection = getAnyConnection();
        var schema = tenantSchemaResolver.resolve(tenantId);
        connection.setSchema(schema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        var defaultSchema = multitenancyProperties.getDefaultSchema();
        connection.setSchema(defaultSchema);
        releaseAnyConnection(connection);
    }
}
