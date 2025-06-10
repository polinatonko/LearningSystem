package org.example.learningsystem.core.multitenancy.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SchemaUtils {

    private static final String SCHEMA_NAME_TEMPLATE = "usr_%s";

    public static String getSchemaName(String tenantId) {
        var processedTenantId = tenantId.replace("-", "_");
        return SCHEMA_NAME_TEMPLATE.formatted(processedTenantId);
    }
}
