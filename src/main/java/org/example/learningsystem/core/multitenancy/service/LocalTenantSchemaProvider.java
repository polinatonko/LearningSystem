package org.example.learningsystem.core.multitenancy.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.multitenancy.config.LocalSchemasProperties;
import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;
import static org.example.learningsystem.core.multitenancy.util.DatabaseUtils.queryForStringList;
import static org.example.learningsystem.core.multitenancy.util.SchemaUtils.getSchemaName;

@Component
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantSchemaProvider implements TenantSchemaProvider {

    private final DataSource dataSource;
    private final LocalSchemasProperties localSchemasProperties;

    @Override
    public String getCurrentTenantSchema() {
        var tenantId = TenantContext.getTenant();
        return getSchemaName(tenantId);
    }

    @Override
    public List<String> getSchemaNames() {
        var names = queryForStringList(dataSource, SELECT_SCHEMAS_SQL);
        return names.stream()
                .filter(this::isUserSchema)
                .toList();
    }

    private boolean isUserSchema(String schema) {
        var systemSchemas = localSchemasProperties.getSystemSchemas();
        return systemSchemas.stream()
                .noneMatch(schema::startsWith);
    }
}
