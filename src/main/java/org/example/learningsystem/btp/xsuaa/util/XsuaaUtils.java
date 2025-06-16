package org.example.learningsystem.btp.xsuaa.util;

import lombok.NoArgsConstructor;
import org.example.learningsystem.multitenancy.context.TenantContext;
import org.example.learningsystem.multitenancy.context.TenantInfo;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class XsuaaUtils {

    private static final String XSUAA_URI_TEMPLATE = "https://%s.authentication.us10.hana.ondemand.com";

    public static Optional<String> getTenantTokenUrl() {
        var tenant = TenantContext.getTenant();
        return Optional.ofNullable(tenant)
                .map(TenantInfo::subdomain)
                .map(XSUAA_URI_TEMPLATE::formatted);
    }
}
