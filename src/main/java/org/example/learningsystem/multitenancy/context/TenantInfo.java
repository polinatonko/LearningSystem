package org.example.learningsystem.multitenancy.context;

import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Represents information about a tenant.
 *
 * @param tenantId  the identifier of the tenant (required)
 * @param subdomain the subdomain of the tenant (optional)
 */
public record TenantInfo(String tenantId, String subdomain) {

    /**
     * Created a {@link TenantInfo} instance with specified tenantId (subdomain will be {@code null}).
     *
     * @param tenantId the tenant identifier
     */
    public TenantInfo(String tenantId) {
        this(tenantId, null);
    }

    @Override
    public boolean equals(Object object) {
        if (isNull(object) || !(object instanceof TenantInfo tenantInfo)) {
            return false;
        }
        return Objects.equals(tenantId, tenantInfo.tenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tenantId);
    }
}
