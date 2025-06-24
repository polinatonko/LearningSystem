package org.example.learningsystem.multitenancy.db.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Utility class for working with tenant database schemas locally.
 * <p>
 * Provides methods for converting between tenant identifiers and schema names using
 * a standardized naming convention.
 */
@NoArgsConstructor(access = PRIVATE)
public class TenantSchemaUtils {

    private static final String TENANT_SCHEMA_NAME_PREFIX = "usr_";

    /**
     * Checks if a schema name follows the tenant schema naming convention.
     *
     * @param schema the schema name
     * @return {@code true} if the provided schema is a tenant schema
     */
    public static boolean isTenantSchema(String schema) {
        return schema.startsWith(TENANT_SCHEMA_NAME_PREFIX);
    }

    /**
     * Returns the tenant identifier for the specified schema.
     *
     * @param schema the schema name
     * @return the tenant identifier
     */
    public static String extractTenantId(String schema) {
        var prefixLength = TENANT_SCHEMA_NAME_PREFIX.length();
        return schema.substring(prefixLength);
    }

    /**
     * Generates a schema name for a tenant identifier.
     *
     * @param tenantId the tenant identifier
     * @return the schema name
     */
    public static String getSchemaName(String tenantId) {
        var processedTenantId = tenantId.replace("-", "_");
        return TENANT_SCHEMA_NAME_PREFIX.concat(processedTenantId);
    }
}
