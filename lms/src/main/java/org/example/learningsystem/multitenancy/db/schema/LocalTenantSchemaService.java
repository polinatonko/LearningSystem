package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Local implementation of {@link TenantSchemaService} that manages tenant schemas through direct SQL execution.
 */
@Service
@Profile("!cloud")
@RequiredArgsConstructor
@Slf4j
public class LocalTenantSchemaService implements TenantSchemaService {

    private final LocalSchemaHelper localSchemaHelper;
    private final TenantSchemaResolver tenantSchemaResolver;

    @Override
    public void create(TenantInfo tenantInfo) {
        var schema = tenantSchemaResolver.resolve(tenantInfo.tenantId());
        log.info("Trying to createSchema new tenant schema: {}", schema);

        localSchemaHelper.create(schema);
        log.info("Created tenant schema: {}", schema);
    }

    @Override
    public void delete(String tenantId) {
        var schema = tenantSchemaResolver.resolve(tenantId);
        log.info("Trying to drop tenant schema: {}", schema);

        localSchemaHelper.drop(schema);
        log.info("Deleted tenant schema: {}", schema);
    }
}
