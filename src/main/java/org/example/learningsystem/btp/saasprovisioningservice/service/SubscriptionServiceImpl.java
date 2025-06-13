package org.example.learningsystem.btp.saasprovisioningservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.saasprovisioningservice.config.ApplicationProperties;
import org.example.learningsystem.btp.saasprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saasprovisioningservice.dto.SubscriptionRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "https://%s-lms-approuter-dev.cfapps.us10-001.hana.ondemand.com";

    private final ApplicationProperties applicationProperties;

    @Override
    public String subscribe(String tenantId, SubscriptionRequestDto subscription) {
        var tenantUrl = TENANT_SPECIFIC_URL_TEMPLATE.formatted(subscription.subscribedSubdomain());
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
