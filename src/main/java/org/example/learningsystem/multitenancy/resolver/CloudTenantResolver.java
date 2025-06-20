package org.example.learningsystem.multitenancy.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Cloud implementation of {@link TenantResolver} that extracts tenant information from JWT tokens.
 */
@Component
@Profile("cloud")
public class CloudTenantResolver implements TenantResolver {

    private static final String EXT_ATTRIBUTES = "ext_attr";
    private static final String SUBDOMAIN_CLAIM = "zdn";
    private static final String ZID_CLAIM = "zid";

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwt) {
            var token = jwt.getToken();
            var tenantId = token.getClaimAsString(ZID_CLAIM);
            var extAttrs = token.getClaimAsMap(EXT_ATTRIBUTES);
            var subdomain = (String) extAttrs.get(SUBDOMAIN_CLAIM);
            return Optional.ofNullable(tenantId)
                    .map(id -> new TenantInfo(id, subdomain));
        }
        return Optional.empty();
    }
}
