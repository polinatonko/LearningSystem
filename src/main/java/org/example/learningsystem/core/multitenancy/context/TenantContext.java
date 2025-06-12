package org.example.learningsystem.core.multitenancy.context;

import lombok.NoArgsConstructor;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TenantContext {

    private static final ThreadLocal<TenantInfo> tenant = new ThreadLocal<>();

    public static TenantInfo getTenant() {
        return tenant.get();
    }

    public static String getTenantId() {
        var tenantInfo = tenant.get();
        return nonNull(tenantInfo) ? tenantInfo.tenantId() : null;
    }

    public static void setTenant(TenantInfo newTenant) {
        tenant.set(newTenant);
    }

    public static void clear() {
        tenant.remove();
    }
}
