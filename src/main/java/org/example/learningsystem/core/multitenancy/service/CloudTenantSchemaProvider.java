package org.example.learningsystem.core.multitenancy.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.binding.dto.ServiceBindingResponseDto;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.core.multitenancy.context.TenantContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantSchemaProvider implements TenantSchemaProvider {

    private static final String SCHEMA_PROPERTY = "schema";

    private final ServiceBindingManager serviceBindingManager;

    @Override
    public String getCurrentTenantSchema() {
        var tenantId = TenantContext.getTenantId();
        var schemaBinding = serviceBindingManager.getByTenantId(tenantId);
        return schemaBinding.credentials().get(SCHEMA_PROPERTY);
    }

    @Override
    public List<String> getSchemaNames() {
        var bindings = serviceBindingManager.getAll();
        return bindings.stream()
                .map(ServiceBindingResponseDto::credentials)
                .map(entry -> entry.get(SCHEMA_PROPERTY))
                .filter(Objects::nonNull)
                .toList();
    }
}
