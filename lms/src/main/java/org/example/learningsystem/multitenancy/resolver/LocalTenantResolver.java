package org.example.learningsystem.multitenancy.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Local implementation of {@link TenantResolver} that extracts tenant information from request
 * {@value TENANT_ID_HEADER} header locally.
 */
@Component
@Profile("!cloud")
public class LocalTenantResolver implements TenantResolver {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var tenant = request.getHeader(TENANT_ID_HEADER);
        return Optional.ofNullable(tenant)
                .map(TenantInfo::new);
    }
}
