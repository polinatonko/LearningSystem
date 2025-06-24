package org.example.learningsystem.btp.servicemanager.common.builder;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Utility class for generating consistent names for SAP BTP service instances and bindings.
 */
@NoArgsConstructor(access = PRIVATE)
public class ServiceNameBuilder {

    private static final String SCHEMA_NAME_TEMPLATE = "%s-schema";
    private static final String BINDING_NAME_TEMPLATE = "%s-schema-binding";

    /**
     * Generates a name of the service instance for the given tenant id.
     *
     * @param tenantId the id of the tenant
     * @return the constructed name of the service instance
     */
    public static String buildInstanceName(String tenantId) {
        return SCHEMA_NAME_TEMPLATE.formatted(tenantId);
    }

    /**
     * Generates a name of the service binding for the given tenant id.
     *
     * @param tenantId the id of the tenant
     * @return the constructed name of the service binding
     */
    public static String buildBindingName(String tenantId) {
        return BINDING_NAME_TEMPLATE.formatted(tenantId);
    }
}
