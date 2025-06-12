package org.example.learningsystem.core.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.multitenancy.config.MultitenancyProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantSchemaResolver implements TenantSchemaResolver {

    private static final String TENANT_SCHEMA_NAME_PREFIX = "usr_";

    private final MultitenancyProperties multitenancyProperties;

    @Override
    public boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }

    @Override
    public String resolveSchema(String tenantId) {
        var defaultSchema = multitenancyProperties.getDefaultSchema();
        var defaultTenant = multitenancyProperties.getDefaultTenant();

        if (nonNull(tenantId) && !tenantId.equals(defaultTenant)) {
            var processedTenantId = tenantId.replace("-", "_");
            return TENANT_SCHEMA_NAME_PREFIX.concat(processedTenantId);
        }
        return defaultSchema;
    }

    @Override
    public String resolveTenantId(String schema) {
        var defaultSchema = multitenancyProperties.getDefaultSchema();
        var defaultTenant = multitenancyProperties.getDefaultTenant();

        if (!schema.equals(defaultSchema)) {
            var prefixLength = TENANT_SCHEMA_NAME_PREFIX.length();
            return schema.substring(prefixLength);
        }
        return defaultTenant;
    }
}
