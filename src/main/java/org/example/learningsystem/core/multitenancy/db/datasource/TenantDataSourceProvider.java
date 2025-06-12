package org.example.learningsystem.core.multitenancy.db.datasource;

import javax.sql.DataSource;
import java.util.Map;

public interface TenantDataSourceProvider {

    Map<String, DataSource> getAll();
}
