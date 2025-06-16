package org.example.learningsystem.multitenancy.context;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TenantContext {

    private static final ThreadLocal<TenantInfo> tenant = new ThreadLocal<>();

    public static TenantInfo getTenant() {
        return tenant.get();
    }

    public static void setTenant(TenantInfo newTenant) {
        tenant.set(newTenant);
    }

    public static void clear() {
        tenant.remove();
    }
}
