package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.multitenancy.util.TenantContext;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

@Slf4j
public abstract class BaseTenantIdentifierFilter extends OncePerRequestFilter {

    private final String defaultTenant;

    protected BaseTenantIdentifierFilter(String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tenant = identifyTenant(request);
        setTenant(tenant);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    protected abstract String identifyTenant(HttpServletRequest request);

    protected void setTenant(String nullableTenant) {
        var tenant = isNull(nullableTenant) ? defaultTenant : nullableTenant;
        TenantContext.setTenant(tenant);
        log.info("Tenant was set: {}", tenant);
    }
}
