package org.example.learningsystem.core.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.db.util.DatabaseUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.CREATE_SCHEMA_SQL;
import static org.example.learningsystem.core.multitenancy.constant.SqlConstants.DROP_SCHEMA_SQL;

@Service
@Profile("!cloud")
@RequiredArgsConstructor
@Slf4j
public class LocalTenantSchemaService implements TenantSchemaService {

    private final DataSource dataSource;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public void create(String tenantId) {
        var schema = tenantSchemaResolver.resolveSchema(tenantId);
        log.info("Trying to create new tenant schema: {}", schema);

        var sql = CREATE_SCHEMA_SQL.formatted(schema);
        DatabaseUtils.executeUpdate(dataSource, sql);
        log.info("Created tenant schema: {}", schema);
    }

    @Override
    public void delete(String tenantId) {
        var schema = tenantSchemaResolver.resolveSchema(tenantId);
        log.info("Trying to drop tenant schema: {}", schema);

        var sql = DROP_SCHEMA_SQL.formatted(schema);
        DatabaseUtils.executeUpdate(dataSource, sql);
        log.info("Deleted tenant schema: {}", schema);
    }
}
