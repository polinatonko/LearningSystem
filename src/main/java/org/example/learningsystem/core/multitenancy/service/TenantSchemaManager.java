package org.example.learningsystem.core.multitenancy.service;

public interface TenantSchemaManager {

    void create(String tenantId);

    void delete(String tenantId);
}
