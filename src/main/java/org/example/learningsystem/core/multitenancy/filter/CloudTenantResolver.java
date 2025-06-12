package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
@Profile("cloud")
public class CloudTenantResolver implements TenantResolver {

    private static final String SUBDOMAIN_CLAIM = "subdomain";
    private static final String ZID_CLAIM = "zid";

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwt) {
            var token = jwt.getToken();
            var tenantId = token.getClaimAsString(ZID_CLAIM);
            var subdomain = token.getClaimAsString(SUBDOMAIN_CLAIM);
            if (nonNull(tenantId)) {
                return Optional.of(new TenantInfo(tenantId, subdomain));
            }
        }
        return Optional.empty();
    }
}
