package org.example.learningsystem.core.multitenancy.config;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.example.learningsystem.core.multitenancy.service.TenantSchemaProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.hibernate.cfg.MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER;

@Component
@Profile("!cloud")
@Slf4j
public class LocalMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;
    private final String defaultTenant;
    private final TenantSchemaProvider tenantSchemaProvider;

    public LocalMultiTenantConnectionProvider(@Value("${multitenancy.default-tenant}") String defaultTenant,
                                              DataSource dataSource,
                                              TenantSchemaProvider tenantSchemaProvider) {
        this.dataSource = dataSource;
        this.defaultTenant = defaultTenant;
        this.tenantSchemaProvider = tenantSchemaProvider;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        log.info("Getting any connection");
        var connection = dataSource.getConnection();
        connection.setSchema(defaultTenant);
        return connection;
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String s) throws SQLException {
        log.info("Getting connection for {} tenant", s);
        var connection = getAnyConnection();
        connection.setSchema(tenantSchemaProvider.getCurrentTenantSchema());
        return connection;
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        connection.setSchema(defaultTenant);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public @UnknownKeyFor @NonNull @Initialized boolean isUnwrappableAs(@UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized ?> aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(@UnknownKeyFor @NonNull @Initialized Class<T> aClass) {
        return null;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
