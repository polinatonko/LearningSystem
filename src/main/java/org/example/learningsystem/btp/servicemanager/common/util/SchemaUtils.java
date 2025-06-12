package org.example.learningsystem.btp.servicemanager.common.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SchemaUtils {

    private static final String SCHEMA_NAME_TEMPLATE = "%s-schema";
    private static final String BINDING_NAME_TEMPLATE = "%s-schema-binding";

    public static String getSchemaName(String tenantId) {
        return SCHEMA_NAME_TEMPLATE.formatted(tenantId);
    }

    public static String getBindingName(String tenantId) {
        return BINDING_NAME_TEMPLATE.formatted(tenantId);
    }
}
