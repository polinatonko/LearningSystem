package org.example.learningsystem.core.multitenancy.db.schema;

import org.example.learningsystem.core.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.core.multitenancy.db.exception.InvalidTenantSchemaException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.example.learningsystem.core.multitenancy.db.constant.TenantConstants.TENANT_SCHEMA_NAME_PREFIX;

@Component
@Profile("!cloud")
public class LocalTenantSchemaResolver implements TenantSchemaResolver {

    private final String defaultSchema;
    private final String defaultTenant;

    public LocalTenantSchemaResolver(MultitenancyProperties multitenancyProperties) {
        defaultSchema = multitenancyProperties.getDefaultSchema();
        defaultTenant = multitenancyProperties.getDefaultTenant();
    }

    @Override
    public String resolve(String tenantId) {
        if (isNull(tenantId) || defaultTenant.equals(tenantId)) {
            return defaultSchema;
        }

        var processedTenantId = tenantId.replace("-", "_");
        return TENANT_SCHEMA_NAME_PREFIX.concat(processedTenantId);
    }

    @Override
    public String resolveTenantId(String schema) {
        if (defaultSchema.equals(schema)) {
            return defaultTenant;
        }

        if (isNull(schema) || !schema.startsWith(TENANT_SCHEMA_NAME_PREFIX)) {
            throw new InvalidTenantSchemaException(schema);
        }

        var prefixLength = TENANT_SCHEMA_NAME_PREFIX.length();
        return schema.substring(prefixLength);
    }
}
