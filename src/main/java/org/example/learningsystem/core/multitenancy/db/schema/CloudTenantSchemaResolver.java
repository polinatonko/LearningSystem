package org.example.learningsystem.core.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.HanaBindingCredentials;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantSchemaResolver implements TenantSchemaResolver {

    private static final String TENANT_SCHEMA_NAME_PREFIX = "USR_";

    private final ServiceBindingManager serviceBindingManager;

    @Override
    public boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }

    @Override
    public String resolveSchema(String tenantId) {
        var binding = serviceBindingManager.getByTenantId(tenantId);
        var credentials = binding.credentials();
        return credentials.getSchema();
    }

    @Override
    public String resolveTenantId(String schema) {
        var bindings = serviceBindingManager.getAll();
        return bindings.stream()
                .map(ServiceBindingResponseDto::credentials)
                .map(HanaBindingCredentials::getSchema)
                .filter(schemaName -> schemaName.equals(schema))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid schema name [schema = %s]".formatted(schema)));
    }
}
