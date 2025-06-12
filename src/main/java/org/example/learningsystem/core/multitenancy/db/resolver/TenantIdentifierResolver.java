package org.example.learningsystem.core.multitenancy.db.resolver;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.core.multitenancy.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

@Component
@RequiredArgsConstructor
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private final MultitenancyProperties multitenancyProperties;

    @Override
    public String resolveCurrentTenantIdentifier() {
        var tenantId = TenantContext.getTenantId();
        var defaultTenant = multitenancyProperties.getDefaultTenant();
        return requireNonNullElse(tenantId, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
