package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
@Profile("!cloud")
public class LocalTenantResolver implements TenantResolver {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var tenant = request.getHeader(TENANT_ID_HEADER);
        return nonNull(tenant) ? Optional.of(new TenantInfo(tenant)) : Optional.empty();
    }
}
