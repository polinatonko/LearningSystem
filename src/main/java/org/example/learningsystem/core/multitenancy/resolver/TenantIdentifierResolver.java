package org.example.learningsystem.core.multitenancy.resolver;

import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private final String defaultTenant;

    public TenantIdentifierResolver(@Value("${multitenancy.default-tenant}") String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        var tenant = TenantContext.getTenant();
        return requireNonNullElse(tenant, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
