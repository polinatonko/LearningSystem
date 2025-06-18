package org.example.learningsystem.btp.saasprovisioningservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.application.config.ApplicationProperties;
import org.example.learningsystem.multitenancy.subscription.dto.ServiceInfoDto;
import org.example.learningsystem.multitenancy.subscription.dto.SubscriptionRequestDto;
import org.example.learningsystem.multitenancy.subscription.service.SubscriptionService;
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
            @Value("${vcap.services.lms-route-user-service.credentials.approuterName}") String approuterName,
            ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.approuterName = approuterName;
    }

    private final ApplicationProperties applicationProperties;

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        var applicationUri = applicationProperties.getUri();
        var approuterUri = applicationUri.replace(applicationProperties.getName(), approuterName);
        var tenantUrl = TENANT_SPECIFIC_URL_TEMPLATE.formatted(HTTPS_PROTOCOL, subscription.subscribedSubdomain(), approuterUri);
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
}
