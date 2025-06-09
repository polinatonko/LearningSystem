package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!cloud")
public class LocalTenantIdentifierFilter extends BaseTenantIdentifierFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    public LocalTenantIdentifierFilter(@Value("${default-tenant}") String defaultTenant) {
        super(defaultTenant);
    }

    @Override
    protected String identifyTenant(HttpServletRequest request) {
        return request.getHeader(TENANT_ID_HEADER);
    }
}
