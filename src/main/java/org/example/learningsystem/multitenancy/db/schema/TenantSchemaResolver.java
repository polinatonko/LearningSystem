package org.example.learningsystem.multitenancy.db.schema;

public interface TenantSchemaResolver {

    String resolve(String tenantId);

    String resolveTenantId(String schema);
}
