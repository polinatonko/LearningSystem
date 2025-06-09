package org.example.learningsystem.core.multitenancy.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TenantContext {

    private static final ThreadLocal<String> tenant = new ThreadLocal<>();

    public static String getTenant() {
        return tenant.get();
    }

    public static void setTenant(String newTenant) {
        tenant.set(newTenant);
    }

    public static void clear() {
        tenant.remove();
    }
}
