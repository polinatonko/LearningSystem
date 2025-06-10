package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class TenantIdentifierFilter extends OncePerRequestFilter {

    private final String defaultTenant;
    private final TenantResolver tenantResolver;

    protected TenantIdentifierFilter(@Value("${multitenancy.default-tenant}") String defaultTenant,
                                     TenantResolver tenantResolver) {
        this.defaultTenant = defaultTenant;
        this.tenantResolver = tenantResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tenant = tenantResolver.resolve(request);
        setTenant(tenant.orElse(defaultTenant));

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    protected void setTenant(String tenant) {
        TenantContext.setTenant(tenant);
        log.info("Tenant was set: {}", tenant);
    }
}
