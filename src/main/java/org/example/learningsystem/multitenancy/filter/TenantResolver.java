package org.example.learningsystem.multitenancy.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;

import java.util.Optional;

public interface TenantResolver {

    Optional<TenantInfo> resolve(HttpServletRequest request);
}
