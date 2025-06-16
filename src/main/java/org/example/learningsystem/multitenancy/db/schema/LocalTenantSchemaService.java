package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.core.db.util.DatabaseUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.example.learningsystem.multitenancy.constant.SqlConstants.CREATE_SCHEMA_SQL;
import static org.example.learningsystem.multitenancy.constant.SqlConstants.DROP_SCHEMA_SQL;

/**
 * Local implementation of {@link TenantSchemaService} that manages tenant schemas through direct SQL execution.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
@Slf4j
public class LocalTenantSchemaService implements TenantSchemaService {

    private final DataSource dataSource;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public void create(TenantInfo tenantInfo) {
        var schema = tenantSchemaResolver.resolve(tenantInfo.tenantId());
        log.info("Trying to create new tenant schema: {}", schema);

        DatabaseUtils.executeSchemaUpdate(dataSource, CREATE_SCHEMA_SQL, schema);
        log.info("Created tenant schema: {}", schema);
    }

    @Override
    public void delete(String tenantId) {
        var schema = tenantSchemaResolver.resolve(tenantId);
        log.info("Trying to drop tenant schema: {}", schema);

        DatabaseUtils.executeSchemaUpdate(dataSource, DROP_SCHEMA_SQL, schema);
        log.info("Deleted tenant schema: {}", schema);
    }
}
