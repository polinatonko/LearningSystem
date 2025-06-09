package org.example.learningsystem.core.multitenancy.util;

public class TenantContext {

    private static final ThreadLocal<String> subdomain = new ThreadLocal<>();

    public static String getSubdomain() {
        return subdomain.get();
    }

    public static void setSubdomain(String newSubdomain) {
        subdomain.set(newSubdomain);
    }

    public static void clear() {
        subdomain.remove();
    }
}
