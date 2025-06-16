package org.example.learningsystem.multitenancy.db.resolver;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.multitenancy.config.MultitenancyProperties;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
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
