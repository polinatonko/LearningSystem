package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.multitenancy.db.exception.InvalidTenantSchemaException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.example.learningsystem.multitenancy.db.util.TenantSchemaUtils.extractTenantId;
import static org.example.learningsystem.multitenancy.db.util.TenantSchemaUtils.getSchemaName;
import static org.example.learningsystem.multitenancy.db.util.TenantSchemaUtils.isTenantSchema;

@Component
@Profile("!cloud")
@RequiredArgsConstructor
public class LocalTenantSchemaResolver implements TenantSchemaResolver {

    private final MultitenancyProperties multitenancyProperties;

    @Override
    public String resolve(String tenantId) {
        if (isNull(tenantId) || tenantId.equals(multitenancyProperties.getDefaultTenant())) {
            return multitenancyProperties.getDefaultSchema();
        }

        return getSchemaName(tenantId);
    }

    @Override
    public String resolveTenantId(String schema) {
        if (isNull(schema) || !isTenantSchema(schema)) {
            throw new InvalidTenantSchemaException(schema);
        }

        if (schema.equals(multitenancyProperties.getDefaultSchema())) {
            return multitenancyProperties.getDefaultTenant();
        }

        return extractTenantId(schema);
    }
}
