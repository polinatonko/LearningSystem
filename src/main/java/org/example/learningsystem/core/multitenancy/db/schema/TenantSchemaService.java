package org.example.learningsystem.core.multitenancy.db.schema;

public interface TenantSchemaService {

    void create(String tenantId);

    void delete(String tenantId);
}
