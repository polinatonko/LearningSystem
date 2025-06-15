package org.example.learningsystem.core.multitenancy.db.resolver;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.core.multitenancy.context.TenantContext;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private final MultitenancyProperties multitenancyProperties;

    @Override
    public String resolveCurrentTenantIdentifier() {
        var tenant = TenantContext.getTenant();
        return Optional.ofNullable(tenant)
                .map(TenantInfo::tenantId)
                .orElse(multitenancyProperties.getDefaultTenant());
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
