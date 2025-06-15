package org.example.learningsystem.core.multitenancy.db.datasource;

import org.example.learningsystem.core.multitenancy.context.TenantInfo;

import javax.sql.DataSource;
import java.util.Map;

public interface TenantDataSourceProvider {

    DataSource create(TenantInfo tenant);

    Map<TenantInfo, DataSource> getAll();
}
