package org.example.learningsystem.multitenancy.db.service;

import org.example.learningsystem.multitenancy.context.TenantInfo;

import javax.sql.DataSource;

public interface TenantDataSourceService {

    DataSource create(TenantInfo tenant);

    void delete(TenantInfo tenant);
}
