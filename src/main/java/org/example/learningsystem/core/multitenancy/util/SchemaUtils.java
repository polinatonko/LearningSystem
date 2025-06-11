package org.example.learningsystem.core.multitenancy.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SchemaUtils {

    public static final String TENANT_SCHEMA_NAME_PREFIX = "usr_";

    public static String getSchemaName(String tenantId) {
        var processedTenantId = tenantId.replace("-", "_");
        return TENANT_SCHEMA_NAME_PREFIX.concat(processedTenantId);
    }
}
