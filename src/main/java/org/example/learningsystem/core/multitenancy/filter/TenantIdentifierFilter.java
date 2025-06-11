package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.exception.InvalidTenantException;
import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantIdentifierFilter extends OncePerRequestFilter {

    private static final String TENANT_PATTERN = "[0-9a-zA-Z\\-_]+";

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

    private void setTenant(String tenant) {
        if (tenant.matches(TENANT_PATTERN)) {
            TenantContext.setTenant(tenant);
            log.info("Tenant was set: {}", tenant);
        } else {
            throw new InvalidTenantException(tenant);
        }
    }
}
