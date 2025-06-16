package org.example.learningsystem.multitenancy.db.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TenantSchemaUtils {

    private static final String TENANT_SCHEMA_NAME_PREFIX = "usr_";

    public static boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }

    public static String extractTenantId(String schema) {
        var prefixLength = TENANT_SCHEMA_NAME_PREFIX.length();
        return schema.substring(prefixLength);
    }

    public static String getSchemaName(String tenantId) {
        var processedTenantId = tenantId.replace("-", "_");
        return TENANT_SCHEMA_NAME_PREFIX.concat(processedTenantId);
    }
}
