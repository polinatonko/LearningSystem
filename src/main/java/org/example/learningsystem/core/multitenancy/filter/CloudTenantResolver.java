package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("cloud")
public class CloudTenantResolver implements TenantResolver {

    private static final String ZID_CLAIM = "zid";

    @Override
    public Optional<String> resolve(HttpServletRequest request) {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwt) {
            var token = jwt.getToken();
            var tenant = token.getClaimAsString(ZID_CLAIM);
            return Optional.ofNullable(tenant);
        }
        return Optional.empty();
    }
}
