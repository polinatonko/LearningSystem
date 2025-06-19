package org.example.learningsystem.multitenancy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.exception.InvalidTenantIdentifierException;
import org.example.learningsystem.multitenancy.resolver.TenantResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Request filter that identifies the current tenant for each request and sets the tenant context.
 * <p>
 * Uses the configured {@link TenantResolver} strategy to extract tenant information from the request and validates
 * the tenant identifier format before setting the context.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TenantIdentifierFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_PATTERN = "[0-9a-zA-Z\\-_]+";

    private final TenantResolver tenantResolver;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tenant = tenantResolver.resolve(request);
        tenant.ifPresent(this::setTenant);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private void setTenant(TenantInfo tenant) {
        var tenantId = tenant.tenantId();
        if (tenantId.matches(TENANT_ID_PATTERN)) {
            TenantContext.setTenant(tenant);
            log.info("Tenant was set: {}", tenant);
        } else {
            throw new InvalidTenantIdentifierException(tenantId);
        }
    }
}
