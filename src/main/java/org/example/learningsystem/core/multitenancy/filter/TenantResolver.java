package org.example.learningsystem.core.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.core.multitenancy.context.TenantInfo;

import java.util.Optional;

public interface TenantResolver {

    Optional<TenantInfo> resolve(HttpServletRequest request);
}
