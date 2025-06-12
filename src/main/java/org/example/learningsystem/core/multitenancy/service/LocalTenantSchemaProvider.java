package org.example.learningsystem.core.multitenancy.service;

import org.example.learningsystem.core.multitenancy.context.TenantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

import static java.util.Objects.requireNonNullElse;
import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.SELECT_SCHEMAS_SQL;
import static org.example.learningsystem.core.multitenancy.util.DatabaseUtils.queryForStringList;
import static org.example.learningsystem.core.multitenancy.util.SchemaUtils.TENANT_SCHEMA_NAME_PREFIX;

@Component
@Profile("!cloud")
public class LocalTenantSchemaProvider implements TenantSchemaProvider {

    private final String defaultSchema;
    private final DataSource dataSource;

    public LocalTenantSchemaProvider(@Value("${multitenancy.default-schema}") String defaultSchema,
                                     DataSource dataSource) {
        this.defaultSchema = defaultSchema;
        this.dataSource = dataSource;
    }

    @Override
    public String getCurrentTenantSchema() {
        var tenantId = TenantContext.getTenantId();
        return requireNonNullElse(tenantId, defaultSchema);
    }

    @Override
    public List<String> getSchemaNames() {
        var names = queryForStringList(dataSource, SELECT_SCHEMAS_SQL);
        return names.stream()
                .filter(this::isTenantSchema)
                .toList();
    }

    private boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }
}
