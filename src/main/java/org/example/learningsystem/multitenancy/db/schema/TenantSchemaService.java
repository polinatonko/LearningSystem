package org.example.learningsystem.multitenancy.db.schema;

import org.example.learningsystem.multitenancy.context.TenantInfo;

public interface TenantSchemaService {

    void create(TenantInfo tenantInfo);

    void delete(String tenantId);
}
