package org.example.learningsystem.multitenancy.context;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Thread-local storage for current tenant information.
 */
@NoArgsConstructor(access = PRIVATE)
public class TenantContext {

    private static final ThreadLocal<TenantInfo> tenant = new ThreadLocal<>();

    /**
     * Returns the current tenant information.
     *
     * @return the current {@link TenantInfo} or {@code null} if no tenant is set
     */
    public static TenantInfo getTenant() {
        return tenant.get();
    }

    /**
     * Sets the tenant information for the current thread.
     *
     * @param newTenant new tenant information to set
     */
    public static void setTenant(TenantInfo newTenant) {
        tenant.set(newTenant);
    }

    /**
     * Clears the tenant information from the current thread.
     * <p>
     * This should be used after processing an http request or other tenant-related operation to avoid leaking
     * tenant context.
     */
    public static void clear() {
        tenant.remove();
    }
}
