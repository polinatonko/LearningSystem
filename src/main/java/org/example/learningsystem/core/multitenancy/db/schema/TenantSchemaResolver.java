package org.example.learningsystem.core.multitenancy.db.schema;

public interface TenantSchemaResolver {

    boolean isTenantSchema(String schema);

    String resolveSchema(String tenantId);

    String resolveTenantId(String schema);
}
