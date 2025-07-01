package org.example.learningsystem.btp.xsuaa.util;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.xsuaa.model.XsuaaProperties;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Provides XSUAA Service URLs based on the current tenant context.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class XsuaaUrlProvider {

    private static final String XSUAA_URI_TEMPLATE = "https://%s.authentication.us10.hana.ondemand.com";

    private final XsuaaProperties xsuaaProperties;

    /**
     * Returns the tenant-specific XSUAA URL based on the current tenant context.
     *
     * @return the tenant-specific XSUAA URL if a tenant context exists with a subdomain,
     * otherwise the configured provider URL
     */
    public String get() {
        var tenant = TenantContext.getTenant();
        var providerUrl = xsuaaProperties.getTokenUrl();
        return Optional.ofNullable(tenant)
                .map(TenantInfo::subdomain)
                .map(XSUAA_URI_TEMPLATE::formatted)
                .orElse(providerUrl);
    }
}
