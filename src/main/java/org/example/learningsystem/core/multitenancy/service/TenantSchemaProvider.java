package org.example.learningsystem.core.multitenancy.service;

import java.util.List;

public interface TenantSchemaProvider {

    String getCurrentTenantSchema();

    List<String> getSchemaNames();
}
