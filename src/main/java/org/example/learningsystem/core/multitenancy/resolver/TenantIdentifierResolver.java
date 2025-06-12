package org.example.learningsystem.core.multitenancy.resolver;

import org.example.learningsystem.core.multitenancy.context.TenantContext;
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
        var tenantId = TenantContext.getTenantId();
        return requireNonNullElse(tenantId, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
