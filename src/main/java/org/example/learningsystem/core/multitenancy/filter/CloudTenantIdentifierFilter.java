package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Profile("cloud")
public class CloudTenantIdentifierFilter extends BaseTenantIdentifierFilter {

    private static final String ZID_CLAIM = "zid";

    public CloudTenantIdentifierFilter(@Value("${default-tenant}") String defaultTenant) {
        super(defaultTenant);
    }

    @Override
    protected String identifyTenant(HttpServletRequest request) {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwt) {
            var token = jwt.getToken();
            return token.getClaimAsString(ZID_CLAIM);
        }
        return null;
    }
}