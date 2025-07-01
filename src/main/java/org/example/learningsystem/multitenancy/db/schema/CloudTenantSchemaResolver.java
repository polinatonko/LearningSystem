package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.HanaBindingCredentials;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.multitenancy.db.exception.InvalidTenantSchemaException;
import org.example.learningsystem.multitenancy.exception.InvalidTenantException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 * Cloud implementation of {@link TenantSchemaResolver} that resolves schema names using SAP BTP service
 * binding information.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantSchemaResolver implements TenantSchemaResolver {

    private final ServiceBindingManager serviceBindingManager;

    @Override
    public String resolve(String tenantId) {
        var binding = serviceBindingManager.getByTenantId(tenantId);
        var credentials = binding.credentials();
        return credentials.getSchema();
    }

    @Override
    public String resolveTenantId(String schema) {
        if (isNull(schema)) {
            throw new InvalidTenantException(schema);
        }

        var bindings = serviceBindingManager.getAll();
        return bindings.stream()
                .map(ServiceBindingResponseDto::credentials)
                .map(HanaBindingCredentials::getSchema)
                .filter(schema::equals)
                .findFirst()
                .orElseThrow(() -> new InvalidTenantSchemaException(schema));
    }
}
