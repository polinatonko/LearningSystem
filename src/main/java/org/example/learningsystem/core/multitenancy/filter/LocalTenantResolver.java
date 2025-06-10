package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("!cloud")
public class LocalTenantResolver implements TenantResolver {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    @Override
    public Optional<String> resolve(HttpServletRequest request) {
        var tenant = request.getHeader(TENANT_ID_HEADER);
        return Optional.ofNullable(tenant);
    }
}
