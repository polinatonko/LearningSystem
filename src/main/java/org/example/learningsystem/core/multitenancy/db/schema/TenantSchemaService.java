package org.example.learningsystem.core.multitenancy.db.schema;

import org.example.learningsystem.core.multitenancy.context.TenantInfo;

public interface TenantSchemaService {

    void create(TenantInfo tenantInfo);

    void delete(String tenantId);
}
