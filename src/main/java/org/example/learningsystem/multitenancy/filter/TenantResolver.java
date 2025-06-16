package org.example.learningsystem.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;

import java.util.Optional;

/**
 * Interface for resolving tenant information from HTTP requests.
 */
public interface TenantResolver {

    /**
     * Resolves tenant information from an HTTP request.
     *
     * @param request a {@link HttpServletRequest} instance
     * @return a {@link Optional} containing the tenant information if resolved
     */
    Optional<TenantInfo> resolve(HttpServletRequest request);
}
