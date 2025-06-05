package org.example.learningsystem.btp.saapprovisioningservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.saapprovisioningservice.config.ServiceDependenciesProperties;
import org.example.learningsystem.btp.saapprovisioningservice.dto.ServiceInfoDto;
import org.example.learningsystem.btp.saapprovisioningservice.dto.SubscriptionRequestDto;
import org.example.learningsystem.btp.saapprovisioningservice.dto.SubscriptionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final String APPLICATION_URL_TEMPLATE = "%s/api/v1/application-info";
    private static final String TENANT_SPECIFIC_URL_TEMPLATE = "%s-%s";

    private final String applicationUrl;
    private final String appRouterUrl;
    private final ServiceDependenciesProperties serviceDependenciesProperties;

    public SubscriptionServiceImpl(@Value("${approuter.url}") String appRouterUrl,
                                   ServiceDependenciesProperties serviceDependenciesProperties) {
        this.appRouterUrl = appRouterUrl;
        this.applicationUrl = APPLICATION_URL_TEMPLATE.formatted(appRouterUrl);
        this.serviceDependenciesProperties = serviceDependenciesProperties;
    }

    @Override
    public SubscriptionResponseDto subscribe(String tenantId, SubscriptionRequestDto subscription) {
        var tenantUrl = TENANT_SPECIFIC_URL_TEMPLATE.formatted(subscription.subscribedSubdomain(), appRouterUrl);
        log.info("Generated tenant url: tenantId = {}, tenantUrl = {}", tenantId, tenantUrl);
        return new SubscriptionResponseDto(tenantUrl, applicationUrl);
    }

    @Override
    public void unsubscribe(String tenantId, SubscriptionRequestDto subscription) {

    }

    @Override
    public List<ServiceInfoDto> getDependencies() {
        return serviceDependenciesProperties.getDependencies()
                .stream()
                .map(ServiceInfoDto::new)
                .toList();
    }
}
