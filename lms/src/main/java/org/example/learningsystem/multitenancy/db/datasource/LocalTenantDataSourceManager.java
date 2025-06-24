package org.example.learningsystem.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.db.schema.LocalSchemaHelper;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaResolver;
import org.example.learningsystem.multitenancy.db.util.TenantSchemaUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Local implementation of {@link TenantDataSourceManager} that uses a single shared data source for all tenants,
 * with schema-based isolation.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceManager implements TenantDataSourceManager {

    private final DataSource dataSource;
    private final LocalSchemaHelper localSchemaHelper;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public DataSource create(TenantInfo tenant) {
        return dataSource;
    }

    @Override
    public Map<TenantInfo, DataSource> getAll() {
        var names = localSchemaHelper.getAll();
        return names.stream()
                .filter(TenantSchemaUtils::isTenantSchema)
                .collect(Collectors.toMap(this::resolveTenantInfo, schema -> dataSource));
    }

    private TenantInfo resolveTenantInfo(String schema) {
        var tenantId = tenantSchemaResolver.resolveTenantId(schema);
        return new TenantInfo(tenantId);
    }
}
