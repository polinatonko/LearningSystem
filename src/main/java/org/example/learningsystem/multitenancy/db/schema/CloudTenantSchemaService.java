package org.example.learningsystem.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.btp.servicemanager.instance.service.ServiceInstanceManager;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.example.learningsystem.btp.servicemanager.common.builder.ServiceNameBuilder.buildBindingName;
import static org.example.learningsystem.btp.servicemanager.common.builder.ServiceNameBuilder.buildInstanceName;

/**
 * Cloud implementation of {@link TenantSchemaService} that manages schemas through SAP BTP
 * service instances and bindings.
 */
@Service
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudTenantSchemaService implements TenantSchemaService {

    private final ServiceBindingManager serviceBindingManager;
    private final ServiceInstanceManager serviceInstanceManager;

    @Override
    public void create(TenantInfo tenantInfo) {
        var schemaName = buildInstanceName(tenantInfo.tenantId());
        var bindingName = buildBindingName(tenantInfo.tenantId());

        var instance = serviceInstanceManager.createByOfferingAndPlanName(schemaName, "hana", "schema");
        log.info("Created service instance {}", instance);

        var instanceId = instance.id();
        var binding = serviceBindingManager.create(bindingName, instanceId, tenantInfo);
        log.info("Created service binding {}", binding);
    }

    @Override
    public void delete(String tenantId) {
        var schemaName = buildInstanceName(tenantId);
        var bindingName = buildBindingName(tenantId);

        serviceBindingManager.deleteByName(bindingName);
        log.info("Removed binding {}", bindingName);

        serviceInstanceManager.deleteByName(schemaName);
        log.info("Removed service instance {}", schemaName);
    }
}
