package org.example.learningsystem.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.db.schema.TenantSchemaResolver;
import org.example.learningsystem.multitenancy.db.util.TenantSchemaUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.learningsystem.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;
import static org.example.learningsystem.core.db.util.DatabaseUtils.queryForStringList;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceProvider implements TenantDataSourceProvider {

    private final DataSource dataSource;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public DataSource create(TenantInfo tenant) {
        return dataSource;
    }

    @Override
    public Map<TenantInfo, DataSource> getAll() {
        var schemas = getSchemaNames(dataSource);
        return schemas.stream()
                .collect(Collectors.toMap(this::resolveTenantInfo, schema -> dataSource));
    }

    private List<String> getSchemaNames(DataSource dataSource) {
        var names = queryForStringList(dataSource, SELECT_SCHEMAS_SQL);
        return names.stream()
                .filter(TenantSchemaUtils::isTenantSchema)
                .toList();
    }

    private TenantInfo resolveTenantInfo(String schema) {
        var tenantId = tenantSchemaResolver.resolveTenantId(schema);
        return new TenantInfo(tenantId);
    }
}
