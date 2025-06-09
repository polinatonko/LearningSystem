package org.example.learningsystem.core.multitenancy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.service.ServiceManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudSchemaManager implements SchemaManager {

    private static final String SCHEMA_NAME_TEMPLATE = "%s-schema";
    private static final String BINDING_NAME_TEMPLATE = "%s-schema-binding";

    private final ServiceManager serviceManager;

    @Override
    public void create(String subdomain) {
        var schemaName = SCHEMA_NAME_TEMPLATE.formatted(subdomain);
        var bindingName = BINDING_NAME_TEMPLATE.formatted(subdomain);

        var instance = serviceManager.createServiceInstanceByOfferingAndPlanName(schemaName, "hana", "schema");
        log.info("Created service instance {}", instance);

        var instanceId = instance.id();
        var binding = serviceManager.createServiceBinding(bindingName, instanceId);
        log.info("Created service binding {}", binding);
    }

    @Override
    public void delete(String subdomain) {
        var schemaName = SCHEMA_NAME_TEMPLATE.formatted(subdomain);
        var bindingName = BINDING_NAME_TEMPLATE.formatted(subdomain);

        serviceManager.deleteServiceBindingByName(bindingName);
        log.info("Removed binding {}", bindingName);

        serviceManager.deleteServiceInstanceByName(schemaName);
        log.info("Removed service instance {}", schemaName);
    }
}
