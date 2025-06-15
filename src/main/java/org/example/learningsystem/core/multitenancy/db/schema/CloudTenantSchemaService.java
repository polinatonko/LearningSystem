package org.example.learningsystem.core.multitenancy.db.schema;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.binding.service.ServiceBindingManager;
import org.example.learningsystem.btp.servicemanager.instance.service.ServiceInstanceManager;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.example.learningsystem.btp.servicemanager.common.util.SchemaUtils.getBindingName;
import static org.example.learningsystem.btp.servicemanager.common.util.SchemaUtils.getSchemaName;

@Service
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudTenantSchemaService implements TenantSchemaService {

    private final ServiceBindingManager serviceBindingManager;
    private final ServiceInstanceManager serviceInstanceManager;

    @Override
    public void create(TenantInfo tenantInfo) {
        var schemaName = getSchemaName(tenantInfo.tenantId());
        var bindingName = getBindingName(tenantInfo.tenantId());

        var instance = serviceInstanceManager.createByOfferingAndPlanName(schemaName, "hana", "schema");
        log.info("Created service instance {}", instance);

        var instanceId = instance.id();
        var binding = serviceBindingManager.create(bindingName, instanceId, tenantInfo);
        log.info("Created service binding {}", binding);
    }

    @Override
    public void delete(String tenantId) {
        var schemaName = getSchemaName(tenantId);
        var bindingName = getBindingName(tenantId);

        serviceBindingManager.deleteByName(bindingName);
        log.info("Removed binding {}", bindingName);

        serviceInstanceManager.deleteByName(schemaName);
        log.info("Removed service instance {}", schemaName);
    }
}
