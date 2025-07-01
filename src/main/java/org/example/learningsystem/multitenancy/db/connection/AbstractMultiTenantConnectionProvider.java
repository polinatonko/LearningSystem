package org.example.learningsystem.multitenancy.db.connection;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static lombok.AccessLevel.PROTECTED;

/**
 * Abstract base class for multitenant connection providers.
 * <p>
 * Provides common functionality for getting and releasing database connections in a multitenant environment.
 * Concrete implementations should handle tenant-aware connection routing.
 */
@RequiredArgsConstructor(access = PROTECTED)
public abstract class AbstractMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {

    protected final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public @UnknownKeyFor @Initialized boolean isUnwrappableAs(@UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized ?> aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(@UnknownKeyFor @NonNull @Initialized Class<T> aClass) {
        return null;
    }
}
