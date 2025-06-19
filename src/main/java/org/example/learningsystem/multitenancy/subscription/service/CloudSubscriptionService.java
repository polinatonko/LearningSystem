package org.example.learningsystem.multitenancy.subscription.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.application.config.ApplicationProperties;
import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("cloud")
@Slf4j
public class CloudSubscriptionService implements SubscriptionService {

    private static final String HTTPS_PROTOCOL = "https";
    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "%s://%s-%s";

    private final String approuterName;

    public CloudSubscriptionService(
            ApplicationProperties applicationProperties,
            @Value("${vcap.services.lms-user-service.credentials.approuter.name}") String approuterName) {
        this.applicationProperties = applicationProperties;
        this.approuterName = approuterName;
    }

    private final ApplicationProperties applicationProperties;

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        var tenantSubdomain = subscription.subscribedSubdomain();
        var tenantUrl = buildTenantUrl(tenantSubdomain);
        log.info("Generated tenant url: tenantId = {}, tenantUrl = {}", tenantId, tenantUrl);
        return tenantUrl;
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {

    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return applicationProperties.getServiceDependencies()
                .stream()
                .map(ServiceInfoDto::new)
                .toList();
    }

    private String buildTenantUrl(String tenantSubdomain) {
        var applicationName = applicationProperties.getName();
        var applicationUri = applicationProperties.getUri();
        var approuterUri = applicationUri.replace(applicationName, approuterName);
        return TENANT_SPECIFIC_URL_TEMPLATE.formatted(HTTPS_PROTOCOL, tenantSubdomain, approuterUri);
    }
}
