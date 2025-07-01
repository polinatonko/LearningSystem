package org.example.learningsystem.btp.xsuaa.util;

import org.example.learningsystem.btp.xsuaa.model.XsuaaProperties;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Provides XSUAA Service URLs based on the current tenant context.
 */
@Component
@Profile("cloud")
public class XsuaaUrlProvider {

    private final String xsuaaUriTemplate;
    private final XsuaaProperties xsuaaProperties;

    public XsuaaUrlProvider(
            @Value("${vcap.services.lms-user-service.credentials.xsuaa.uriTemplate}") String xsuaaUriTemplate,
            XsuaaProperties xsuaaProperties) {
        this.xsuaaUriTemplate = xsuaaUriTemplate;
        this.xsuaaProperties = xsuaaProperties;
    }

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
                .map(xsuaaUriTemplate::formatted)
                .orElse(providerUrl);
    }
}
