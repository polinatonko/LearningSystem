package org.example.learningsystem.core.multitenancy.context;

import java.util.Objects;

import static java.util.Objects.isNull;

public record TenantInfo(String tenantId, String subdomain) {

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
