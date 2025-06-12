package org.example.learningsystem.core.multitenancy.db.datasource;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.multitenancy.db.schema.TenantSchemaResolver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;
import static org.example.learningsystem.core.multitenancy.db.util.DatabaseUtils.queryForStringList;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantDataSourceProvider implements TenantDataSourceProvider {

    private final DataSource dataSource;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public Map<String, DataSource> getAll() {
        var schemas = getSchemaNames(dataSource);
        return schemas.stream()
                .collect(Collectors.toMap(tenantSchemaResolver::resolveTenantId, schema -> dataSource));
    }

    private List<String> getSchemaNames(DataSource dataSource) {
        var names = queryForStringList(dataSource, SELECT_SCHEMAS_SQL);
        return names.stream()
                .filter(tenantSchemaResolver::isTenantSchema)
                .toList();
    }
}
