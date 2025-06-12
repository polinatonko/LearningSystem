package org.example.learningsystem.core.multitenancy.context;

public record TenantInfo(String tenantId, String subdomain) {

    public TenantInfo(String tenantId) {
        this(tenantId, null);
    }
}
