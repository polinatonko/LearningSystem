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
        log.debug("Trying to create new tenant schema [name = {}]", schema);

        localSchemaHelper.create(schema);
        log.info("Created tenant schema [name = {}]", schema);
    }

    @Override
    public void delete(String tenantId) {
        var schema = tenantSchemaResolver.resolve(tenantId);
        log.debug("Trying to drop tenant schema [name = {}]", schema);

        localSchemaHelper.drop(schema);
        log.info("Deleted tenant schema [name = {}]", schema);
    }
}
