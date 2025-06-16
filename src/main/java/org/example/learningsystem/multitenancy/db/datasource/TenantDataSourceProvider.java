package org.example.learningsystem.multitenancy.db.datasource;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;
import java.util.Map;

public interface TenantDataSourceProvider {

    DataSource create(TenantInfo tenant);

    Map<TenantInfo, DataSource> getAll();
}
