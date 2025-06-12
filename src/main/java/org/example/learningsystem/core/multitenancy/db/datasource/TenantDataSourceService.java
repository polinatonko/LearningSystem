package org.example.learningsystem.core.multitenancy.db.datasource;

import javax.sql.DataSource;

public interface TenantDataSourceService {

    DataSource create(String tenantId);

    void delete(String tenantId);
}
