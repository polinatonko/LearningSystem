package org.example.learningsystem.multitenancy.resolver;

import com.sap.cloud.security.xsuaa.token.XsuaaToken;
import jakarta.servlet.http.HttpServletRequest;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.example.learningsystem.core.security.util.SecurityUtils.retrieveXsuaaTokenFromRequest;

/**
 * Cloud implementation of {@link TenantResolver} that extracts tenant information from JWT tokens.
 */
@Component
@Profile("cloud")
public class CloudTenantResolver implements TenantResolver {

    @Override
    public Optional<TenantInfo> resolve(HttpServletRequest request) {
        var xsuaaTokenOpt = retrieveXsuaaTokenFromRequest(request);
        return xsuaaTokenOpt.flatMap(this::retrieveTenantInfoFromToken);
    }

    private Optional<TenantInfo> retrieveTenantInfoFromToken(XsuaaToken token) {
        var tenantId = token.getZoneId();
        var subdomain = token.getSubdomain();
        return Optional.ofNullable(tenantId)
                .map(id -> new TenantInfo(id, subdomain));
    }
}
