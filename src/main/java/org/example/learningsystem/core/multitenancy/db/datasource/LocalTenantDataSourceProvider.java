package org.example.learningsystem.core.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.db.util.DataSourceHelper;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;
import static org.example.learningsystem.core.multitenancy.db.constant.TenantConstants.TENANT_SCHEMA_NAME_PREFIX;
import static org.example.learningsystem.core.multitenancy.db.util.DatabaseUtils.queryForStringList;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceProvider implements TenantDataSourceProvider {

    private final DataSource dataSource;
    private final DataSourceHelper dataSourceHelper;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public DataSource create(TenantInfo tenant) {
        var schema = tenantSchemaResolver.resolve(tenant.tenantId());
        return dataSourceHelper.createForSchema(schema);
    }

    @Override
    public Map<TenantInfo, DataSource> getAll() {
        var schemas = getSchemaNames(dataSource);
        return schemas.stream()
                .collect(Collectors.toMap(this::resolveTenantInfo, dataSourceHelper::createForSchema));
    }

    private List<String> getSchemaNames(DataSource dataSource) {
        var names = queryForStringList(dataSource, SELECT_SCHEMAS_SQL);
        return names.stream()
                .filter(this::isTenantSchema)
                .toList();
    }

    private TenantInfo resolveTenantInfo(String schema) {
        var tenantId = tenantSchemaResolver.resolveTenantId(schema);
        return new TenantInfo(tenantId);
    }

    private boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }
}
