package org.example.learningsystem.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * Cloud implementation of {@link TenantResolver} that extracts tenant information from JWT tokens.
 */
@Component
@Profile("cloud")
public class CloudTenantResolver implements TenantResolver {

    private static final String SUBDOMAIN_CLAIM = "zdn";
    private static final String ZID_CLAIM = "zid";

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwt) {
            var token = jwt.getToken();
            var tenantId = token.getClaimAsString(ZID_CLAIM);
            var extAttrs = token.getClaimAsMap("ext_attr");
            var subdomain = (String) extAttrs.get(SUBDOMAIN_CLAIM);
            if (nonNull(tenantId)) {
                return Optional.of(new TenantInfo(tenantId, subdomain));
            }
        }
        return Optional.empty();
    }
}
