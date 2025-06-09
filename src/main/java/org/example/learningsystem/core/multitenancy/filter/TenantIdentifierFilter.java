package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantIdentifierFilter extends OncePerRequestFilter {

    private static final String EXT_ATTRIBUTES = "ext_attr";
    private static final String SUBACCOUNT_ID = "subaccountid";
    private static final String SUBDOMAIN = "sdn";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var principal = request.getUserPrincipal();
        if (principal instanceof JwtAuthenticationToken jwtToken) {
            var token = jwtToken.getToken();
            identifyTenant(token);
        }
        filterChain.doFilter(request, response);
    }

    private void identifyTenant(Jwt token) {
        log.info("Jwt claims: {}", token.getClaims());

        var extAttributes = token.getClaimAsMap(EXT_ATTRIBUTES);
        if (nonNull(extAttributes)) {
            var tenantId = extAttributes.get(SUBACCOUNT_ID);
            var subdomain = (String) extAttributes.get(SUBDOMAIN);
            log.info("Inspect jwt token: tenantId = {}, subdomain = {}", tenantId, subdomain);
            setSubdomain(subdomain);
        }
    }

    private void setSubdomain(String subdomain) {
        TenantContext.setSubdomain(subdomain);
        log.info("Subdomain was set [subdomain = {}]", subdomain);
    }
}